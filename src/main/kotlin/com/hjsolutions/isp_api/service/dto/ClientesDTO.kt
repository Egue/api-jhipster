package com.hjsolutions.isp_api.service.dto

import java.io.Serializable

data class ClientesContratoDTO(
    val idContrato: Long,
    val tipoCliente: String,
    val nameCliente : String,
    val nameServicio: String,
    val direccion: String,
    val parcial : Double,
    val total : Double

): Serializable{}
