<?php

namespace App\Http\Controllers\Api\V1;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use App\Models\Cuenta;
use Illuminate\Support\Facades\Log;
use Illuminate\Support\Facades\Hash;
use App\Services\OKTVService;

class CuentaController extends Controller
{
    /**
     * GET: Listar todas las cuentas para el Angular Dashboard
     */
    public function index() 
    { 
        return response()->json(Cuenta::orderBy('created_at', 'desc')->get(), 200); 
    }

    /**
     * GET: Ver un contrato específico (Ruta /microok/cortes/{id})
     */
    public function show($user_id)
    {
        $cuenta = Cuenta::where('id_cuenta', $user_id)
                        ->orWhere('id_contrato', $user_id)
                        ->first();

        if (!$cuenta) {
            return response()->json(['message' => 'Contrato no encontrado en Casanare'], 404);
        }

        return response()->json($cuenta, 200);
    }

    /**
     * POST: Crear usuario y asignar a operador
     */
    public function store(Request $request, OKTVService $oktv) 
    {
        $request->validate([
            'email' => 'required|email',
            'username' => 'required',
            'password' => 'required',
            'group_id' => 'required'
        ]);

        $passwordPlano = $request->password;

        $resultado = $oktv->registrarEnOKTV([
            'email' => $request->email,
            'username' => $request->username,
            'password' => $passwordPlano
        ]);

        if ($resultado['status'] >= 400) {
            return response()->json($resultado['body'] ?? ['message' => $resultado['error']], $resultado['status']);
        }

        $cuenta = Cuenta::updateOrCreate(
            ['email' => $request->email],
            [
                'id_cuenta'   => $resultado['body']['user_id'] ?? null,
                'username'    => $request->username,
                'password'    => Hash::make($passwordPlano),
                'oktv_sync'   => 'success',
                'estado'      => 'activo',
                'group_id'    => $request->group_id,
                'group_role'  => $request->group_role ?? 'operators-user',
                'id_contrato' => $request->id_contrato ?? 'TEMP-'.time()
            ]
        );

        return response()->json($resultado['body'], 201);
    }

    /**
     * PATCH: Actualizar datos (Password, Rol, Publicar)
     */
    public function update(Request $request, $user_id, OKTVService $oktv)
    {
        try {
            $groupId = $request->input('group_id') ?? $request->query('group_id');

            if (!$groupId) {
                return response()->json([
                    'message' => 'Error de validación',
                    'error' => 'El campo group_id es obligatorio para sincronizar con OKTV.'
                ], 422);
            }

            // Llamada al servicio externo OKTV
            $resultado = $oktv->actualizarUsuario($user_id, [
                'password' => $request->new_password,
                'role'     => $request->new_role,
                'publish'  => $request->publish,
                'group_id' => $groupId
            ]);

            // === ACTUALIZACIÓN LOCAL EN YOPAL ===
            // Si la API externa respondió con éxito (200-299)
            if (isset($resultado['status']) && $resultado['status'] < 300) {
                $datosLocal = [];

                if ($request->has('new_password')) {
                    $datosLocal['password'] = Hash::make($request->new_password);
                }

                if ($request->has('new_role')) {
                    $datosLocal['group_role'] = $request->new_role;
                }

                if ($request->has('publish')) {
                    $datosLocal['estado'] = $request->publish ? 'activo' : 'inactivo';
                }

                if (!empty($datosLocal)) {
                    Cuenta::where('id_cuenta', $user_id)->update($datosLocal);
                    Log::info("DB Local actualizada para $user_id", $datosLocal);
                }
            }

            return response()->json($resultado['body'] ?? ['message' => 'Actualizado con éxito'], $resultado['status'] ?? 200);

        } catch (\Exception $e) {
            Log::error("Error en Update Yopal para $user_id: " . $e->getMessage());
            return response()->json([
                'message' => 'Error crítico en el proceso de actualización',
                'error' => $e->getMessage()
            ], 500);
        }
    }

    /**
     * DELETE: Eliminar de operador (Suspensión)
     */
    public function destroy($user_id, $group_id = null, OKTVService $oktv)
    {
        try {
            try {
                $oktv->eliminarDeOperador($user_id);
            } catch (\Exception $e) {
                Log::warning("Fallo conexión OKTV para $user_id: " . $e->getMessage());
            }

            $cuenta = Cuenta::where('id_cuenta', $user_id)->orWhere('id_contrato', $user_id)->first();

            if ($cuenta) {
                $cuenta->update(['oktv_sync' => 'removed', 'estado' => 'inactivo']);
                return response()->json(['message' => 'Usuario suspendido/removido correctamente'], 200);
            }

            return response()->json(['message' => 'No se encontró el registro local'], 404);

        } catch (\Exception $e) {
            return response()->json(['message' => 'Error en destroy', 'error' => $e->getMessage()], 500);
        }
    }

    /**
     * POST: Procesar cortes masivos (Cron Mensual)
     */
    public function procesarCortesMensuales(Request $request, OKTVService $oktv)
    {
        $contratos = $request->input('contratos', []);
        $procesados = [];

        foreach ($contratos as $id_contrato) {
            $this->destroy($id_contrato, null, $oktv);
            $procesados[] = $id_contrato;
        }

        return response()->json([
            'message' => 'Proceso de cortes mensuales finalizado',
            'total_procesados' => count($procesados),
            'listado' => $procesados
        ], 200);
    }
}