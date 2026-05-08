<?php
$jsonFile  = 'datos.json';
$logFile   = 'respuestas.json';
$endpoint  = 'http://api.server.internetinalambrico.com.co'; // � Cambia esto

if (!file_exists($jsonFile)) {
    die("❌ Archivo no encontrado: $jsonFile\n");
}

$facturas = json_decode(file_get_contents($jsonFile), true);
//$facturas = array_slice($facturas , 0 , 1);

if (empty($facturas)) {
    die("❌ El JSON está vacío o mal formado\n");
}

// Cargar respuestas previas si ya existe el archivo
$respuestas = file_exists($logFile)
    ? json_decode(file_get_contents($logFile), true) ?? []
    : [];

// Facturas ya procesadas (para no repetir en caso de reinicio)
$procesadas = array_column($respuestas, 'factura_numero');

$total     = count($facturas);
$enviadas  = 0;
$errores   = 0;

foreach ($facturas as $index => $factura) {
    $numFact = $factura['factura_numero'];

    // Saltar si ya fue procesada
    if (in_array($numFact, $procesadas)) {
        echo "⏭️  Factura #$numFact ya procesada, omitiendo...\n";
        continue;
    }

    $actual = $index + 1;
    echo "� [{$actual}/{$total}] Enviando factura #$numFact...\n";

    $ch = curl_init($endpoint);
    curl_setopt_array($ch, [
        CURLOPT_POST           => true,
        CURLOPT_POSTFIELDS     => json_encode($factura),
        CURLOPT_RETURNTRANSFER => true,
        CURLOPT_HTTPHEADER     => [
            'Content-Type: application/json',
            'Accept: application/json',
        ],
        CURLOPT_TIMEOUT        => 60,
    ]);

    $response  = curl_exec($ch);
    $httpCode  = curl_getinfo($ch, CURLINFO_HTTP_CODE);
    $curlError = curl_error($ch);
    curl_close($ch);

    $respJson = $response ? json_decode($response, true) : null;

    // Armar registro con los campos clave
    $registro = [
        "factura_numero"       => $numFact,
        "cliente_nombre"       => $factura['cliente_nombre'],
        "cliente_identificacion" => $factura['cliente_identificacion'],
        "fecha_envio"          => date('Y-m-d H:i:s'),
        "http_code"            => $httpCode,
        "error_curl"           => $curlError ?: null,
        "factura_creada"       => $respJson['factura_creada'] ?? null,
        "estado_dian"          => $respJson['estado_factura_dian'] ?? null,
        "cufe"                 => $respJson['cufe'] ?? null,
        "key_dian"             => $respJson['key_dian'] ?? null,
        "url_qr"               => $respJson['url_qr'] ?? null,
        "url_xml_firmado"      => $respJson['url_xml_firmado'] ?? null,
        "url_xml_AttachedDocument" => $respJson['url_xml_AttachedDocument'] ?? null,
        "respuesta_completa"   => $respJson,
    ];

    if ($curlError) {
        echo "❌ Error cURL: $curlError\n";
        $errores++;
    } elseif (!empty($respJson['factura_creada']) && $respJson['factura_creada'] === true) {
        echo "✅ HTTP $httpCode | DIAN: {$respJson['estado_factura_dian']}\n";
        echo "   CUFE: {$respJson['cufe']}\n";
        $enviadas++;
    } else {
        echo "⚠️  HTTP $httpCode | Respuesta inesperada\n";
        $errores++;
    }

    // Guardar inmediatamente
    $respuestas[] = $registro;
    file_put_contents($logFile, json_encode($respuestas, JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE));
    echo "� Guardado en $logFile\n";

    // Resumen parcial
    echo "� Progreso: $enviadas enviadas, $errores errores de $total total\n\n";

    // Esperar 30 segundos antes de la siguiente
    if ($index < $total - 1) {
        echo "⏳ Esperando 30 segundos...\n\n";
        sleep(6);
    }
}

echo "═══════════════════════════════════\n";
echo "✅ Proceso terminado\n";
echo "� Enviadas correctamente : $enviadas\n";
echo "❌ Con errores            : $errores\n";
echo "� Total facturas         : $total\n";
echo "� Log guardado en        : $logFile\n";