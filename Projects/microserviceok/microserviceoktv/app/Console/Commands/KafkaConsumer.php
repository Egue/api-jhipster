<?php

namespace App\Console\Commands;

use Illuminate\Console\Command;
use LongLang\PhpKafka\Consumer\ConsumerConfig;
use LongLang\PhpKafka\Consumer\Consumer;
use App\Models\Cuenta;
use App\Services\OKTVService;
use Illuminate\Support\Facades\Log;

class KafkaConsumer extends Command
{
    protected $signature = 'kafka:consume';
    protected $description = 'Consume eventos desde Control 1 (contratook.create, pago.create, pago.anulado)';

    public function handle(OKTVService $oktvService)
    {
        $this->info("=== MICROSERVICE OKTV (KAFKA CONSUMER) ===");

        // Carga manual (por si falla autoload)
        require_once base_path('vendor/longlang/phpkafka/src/Config/AbstractConfig.php');
        require_once base_path('vendor/longlang/phpkafka/src/Consumer/ConsumerConfig.php');
        require_once base_path('vendor/longlang/phpkafka/src/Consumer/Consumer.php');

        try {
            $config = new ConsumerConfig();
            $config->setBootstrapServer(env('KAFKA_BROKERS', '192.168.131.11:9092'));
            $config->setGroupId(env('KAFKA_CONSUMER_GROUP_ID', 'microok_group'));
            $config->setClientId('microservice_yopal');

            $config->setTopic([
                'pago.create',
                'contratook.create',
                'pago.anulado'
            ]);

            $consumer = new Consumer($config);

            $this->info(">>> CONECTADO A KAFKA: " . env('KAFKA_BROKERS'));

            while (true) {
                $message = $consumer->consume();

                if ($message) {
                    $payloadRaw = json_decode($message->getValue(), true);
                    $topic = $message->getTopic();

                    if (!$payloadRaw) {
                        $this->error("Payload inválido (no JSON)");
                        continue;
                    }

                    $evento = $payloadRaw['event'] ?? $topic;
                    $data   = $payloadRaw['data'] ?? [];

                    // PRIORIDAD: id_contrato del root
                    $idContrato = $payloadRaw['id_contrato'] ?? ($data['id_contrato'] ?? null);

                    // Validación de inconsistencia
                    if (
                        isset($payloadRaw['id_contrato'], $data['id_contrato']) &&
                        $payloadRaw['id_contrato'] != $data['id_contrato']
                    ) {
                        Log::warning("ID inconsistente en evento Kafka", [
                            'root' => $payloadRaw['id_contrato'],
                            'data' => $data['id_contrato'],
                            'evento' => $evento
                        ]);
                    }

                    if ($idContrato) {
                        $this->warn("¡Evento [{$evento}] detectado para Contrato: {$idContrato}");
                        $this->procesarMensaje($evento, $payloadRaw, $oktvService);
                    } else {
                        $this->error("Payload sin id_contrato. Ignorando...");
                    }

                    $consumer->ack($message);
                }
            }

        } catch (\Exception $e) {
            $this->error("Error crítico en el consumidor: " . $e->getMessage());
            Log::error("KafkaConsumer Critical Error: " . $e->getMessage());
        }
    }

    private function procesarMensaje($evento, $payloadRaw, OKTVService $oktvService)
    {
        $data = $payloadRaw['data'] ?? [];

        // Tomamos SIEMPRE el id correcto
        $idContrato = $payloadRaw['id_contrato'] ?? ($data['id_contrato'] ?? null);
        $idCliente  = $data['id_cliente'] ?? null;
        $servicio   = $data['servicio'] ?? null;

        // Solo eventos de contrato OKTV
        if ($evento === 'contrato.created' && $servicio === 'oktv') {

            try {
                Cuenta::updateOrCreate(
                    ['id_contrato' => $idContrato],
                    [
                        'id_cuenta' => null,
                        'estado'    => 'activo',
                        'oktv_sync' => 'pending',
                        'email'     => "contrato_{$idContrato}@ok.local",
                        'username'  => "user_{$idContrato}",
                        'password'  => bcrypt($idContrato)
                    ]
                );

                $this->info("Registro base creado para contrato: {$idContrato}");

            } catch (\Exception $e) {
                $this->error("Error al registrar contrato {$idContrato}: " . $e->getMessage());
                Log::error("Error contrato {$idContrato}", [
                    'error' => $e->getMessage()
                ]);
            }
        }
    }
}