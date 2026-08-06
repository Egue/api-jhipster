<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{

    public function up(): void
    {
        Schema::create('cuentas', function (Blueprint $table) {
            $table->id();


            $table->string('id_contrato')->unique();
            $table->string('id_cuenta')->nullable()->index();
            $table->string('nombre_cliente')->nullable();

            $table->string('email')->index();
            $table->string('username')->index();

            $table->string('password');

            $table->string('group_id')->default(env('OKTV_GROUP_ID', '50'));
            $table->string('group_role')->default('operators-user');

            $table->enum('estado', ['activo', 'inactivo'])->default('activo');
            $table->string('oktv_sync')->default('pending')->index();

            $table->timestamps();
        });
    }

    public function down(): void
    {
        Schema::dropIfExists('cuentas');
    }
};