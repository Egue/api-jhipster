package com.hjsolutions.isp_api.service.dto

import java.io.Serializable

data class OrdenArticuloDTO(
    val idUsuario : Number?,
    val empleado : String,
    val articulo: String,
    val nota : String,
    val nota_final : String,
    val fechaRegistro: Number,
    val fechaAsiste : Number,
    val cliente : String,
    val estacion: String,
    val idContrato : Number,
    val idEstacion : Number,
    val cantidadEmpresa: Number,
    val cantidadUsuario:Number
): Serializable {}
