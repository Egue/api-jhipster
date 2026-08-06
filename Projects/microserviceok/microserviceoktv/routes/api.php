<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\Api\V1\CuentaController;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider and all of them will
| be assigned to the "api" middleware group. Make something great!
|
*/

Route::get('/ping', function () {
    return response()->json([
        'status' => 'online',
        'framework' => 'Laravel 10',
        'mensaje' => 'Microservicio respondiendo correctamente'
    ]);
});

Route::apiResource('user-group-manager', CuentaController::class)
    ->parameters(['user-group-manager' => 'user_id'])
    ->only(['store', 'update']);

Route::delete('/user-group-manager/{user_id}/group/{group_id}', [CuentaController::class, 'destroy']);

Route::prefix('microok')->group(function () {
    Route::get('/cuentas', [CuentaController::class, 'index']);    
    Route::post('/cuentas/create', [CuentaController::class, 'store']);
    Route::get('/cortes/{user_id}', [CuentaController::class, 'show']);      // GET para el Dashboard
});

Route::post('/cron/cortes/mensuales', [CuentaController::class, 'procesarCortesMensuales']);
