<?php

namespace App\Services;

use Illuminate\Support\Facades\Http;
use Illuminate\Support\Facades\Log;

class OKTVService
{
    protected $baseUrl;
    protected $groupId;

    public function __construct()
    {
        $this->baseUrl = env('OKTV_BASE_URL');
        $this->groupId = env('OKTV_GROUP_ID');
    }

    public function getAccessToken()
    {
        if (env('OKTV_SKIP_API', false)) {
            return 'fake-token-for-testing';
        }

        try {
            $authUrl = env('OKTV_AUTH_URL');
            
            if (!$authUrl) {
                Log::error('OKTV_AUTH_URL not configured');
                return null;
            }

            $response = Http::withoutVerifying()
                ->asForm()
                ->post($authUrl, [
                    'grant_type'    => 'client_credentials',
                    'client_id'     => env('OKTV_CLIENT_ID'),
                    'client_secret' => env('OKTV_CLIENT_SECRET'),
                ]);

            if ($response->successful()) {
                return $response->json()['access_token'] ?? null;
            }

            Log::error('OKTV Auth error', [
                'status' => $response->status(),
                'body' => $response->body()
            ]);

            return null;

        } catch (\Exception $e) {
            Log::error('OKTV Token Exception: ' . $e->getMessage());
            return null;
        }
    }


    public function registrarEnOKTV($datos)
    {
        if (env('OKTV_SKIP_API', false)) {
            return [
                'status' => 201,
                'body' => [
                    'user_id' => 'OKTV-' . rand(1000, 9999),
                    'message' => "User created and added to the group successfully with the 'operators-user' role."
                ]
            ];
        }

        $token = $this->getAccessToken();

        if (!$token) {
            return ['error' => 'Auth fail', 'status' => 401];
        }

        try {
            $response = Http::withoutVerifying()
                ->withToken($token)
                ->post("{$this->baseUrl}/user-group-manager", [
                    'email'      => $datos['email'],
                    'username'   => $datos['username'],
                    'password'   => $datos['password'],
                    'group_id'   => $this->groupId,
                    'group_role' => 'operators-user'
                ]);

            if (!$response->successful()) {
                Log::error('OKTV Create User error', [
                    'status' => $response->status(),
                    'body' => $response->body()
                ]);
            }

            return [
                'body' => $response->json(),
                'status' => $response->status()
            ];
if (env('OKTV_SKIP_API', false)) {
            return [
                'status' => 200,
                'body' => [
                    'message' => 'User data updated successfully.'
                ]
            ];
        }

        
        } catch (\Exception $e) {
            Log::error('OKTV Create Exception: ' . $e->getMessage());
            return ['error' => 'Exception creating user', 'status' => 500];
        }
    }


    public function actualizarUsuario($userId, $datosNuevos)
    {
        $token = $this->getAccessToken();

        if (!$token) {
            return ['error' => 'Auth fail', 'status' => 401];
        }

        $payload = ['group_id' => $this->groupId];

        if (!empty($datosNuevos['password'])) {
            $payload['new_password'] = $datosNuevos['password'];
        }

        if (!empty($datosNuevos['role'])) {
            $payload['new_role'] = $datosNuevos['role'];
        }

        if (isset($datosNuevos['publish'])) {
            $payload['publish'] = $datosNuevos['publish'];
        }

        try {
            $response = Http::withoutVerifying()
                ->withToken($token)
                ->patch("{$this->baseUrl}/user-group-manager/{$userId}", $payload);

            if (!$response->successful()) {
                Log::error('OKTV Update error', [
                    'status' => $response->status(),
                    'body' => $response->body()
                ]);
            }

            return [
                'body' => $response->json(),
                'status' => $response->status()
            ];
            if (env('OKTV_SKIP_API', false)) {
            return [
                'status' => 200,
                'body' => [
                    'message' => 'User removed from group successfully.'
                ]
            ];
        }

        
        } catch (\Exception $e) {
            Log::error('OKTV Update Exception: ' . $e->getMessage());
            return ['error' => 'Exception updating user', 'status' => 500];
        }
    }

    public function eliminarDeOperador($userId)
    {
        $token = $this->getAccessToken();

        if (!$token) {
            return ['error' => 'Auth fail', 'status' => 401];
        }

        try {
            $response = Http::withoutVerifying()
                ->withToken($token)
                ->delete("{$this->baseUrl}/user-group-manager/{$userId}/group/{$this->groupId}");

            if (!$response->successful()) {
                Log::error('OKTV Delete error', [
                    'status' => $response->status(),
                    'body' => $response->body()
                ]);
            }

            return [
                'body' => $response->json(),
                'status' => $response->status()
            ];

        } catch (\Exception $e) {
            Log::error('OKTV Delete Exception: ' . $e->getMessage());
            return ['error' => 'Exception deleting user', 'status' => 500];
        }
    }
}