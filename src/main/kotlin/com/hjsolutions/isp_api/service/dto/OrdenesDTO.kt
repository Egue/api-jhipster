package com.hjsolutions.isp_api.service.dto

import java.io.Serializable

data class OrdenesDTO(
    val id:Long,
    val causa:String,
    val cliente:String,
    val direccion:String,
    val registroFecha:String,
    val asignaFecha:String,
    val asisteFecha:String,
    val usuarioEjecuta:Long,
    val nota:String,
    val contrato:Long
): Serializable {}

data class createDTO(
    var typeTransfer: Long = 0,
    var idContrato:Long = 0,
    var observation:String = "",
    var userId:Long = 0,
    var typeOrden : Long = 0,
    var idEstacion:Long = 0
): Serializable{}
