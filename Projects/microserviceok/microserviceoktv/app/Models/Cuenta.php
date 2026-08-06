<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Cuenta extends Model
{
    use HasFactory;

    protected $table = 'cuentas';

    protected $fillable = [
        'id_contrato',
        'id_cuenta',
        'nombre_cliente',
        'email',
        'username',
        'password',
        'group_id',
        'group_role',
        'estado',
        'oktv_sync' 
    ];

    protected $attributes = [
        'group_id' => '50',
        'group_role' => 'operators-user',
        'estado' => 'activo',
        'oktv_sync' => 'pending'
    ];


    protected $hidden = [
        'password'
    ];


    protected $casts = [
        'created_at' => 'datetime',
        'updated_at' => 'datetime',
    ];
}