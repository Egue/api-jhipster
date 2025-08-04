package com.hjsolutions.isp_api.domain

import com.comunicamosmas.api.domain.Cliente
import com.comunicamosmas.api.domain.Direccion
import com.comunicamosmas.api.domain.Estacion
import com.comunicamosmas.api.domain.Usuario
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable
import org.springframework.data.annotation.Id;
import springfox.documentation.spring.web.json.Json
import java.time.LocalDateTime

@Document(collection = "ordenes_servicio")
data class OrdenesServicio(
    @Id
    val id: ObjectId? =null,
    val fechaRegistro: LocalDateTime? = LocalDateTime.now(),
    val userAsigna: Usuario? = null,
    val userEjecuta: Usuario? = null,
    val userRegistra: Usuario? = null,
    val userAnula:Usuario? = null,
    val tipoTecnologia: String? ="",
    val tipoServicio: String? = "",
    val numeroA : Number? = 0,
    val numeroB: Number? = 0,
    val idContrato: Number? = 0,
    val Cliente: Cliente? = null,
    val estacion: Estacion? = null,
    val direccion: Direccion? = null,
    val causaSolicitud: String? = "",
    val estado: String? ="",
    val comentarioInicial:String? ="",
    val comentarioFinal:String? = "",
    val mikrotik : MikrotikEjecuta? = null,
    val infoTecnologia: Json? = null,
    val log:String? =""
    ): Serializable{}

data class MikrotikEjecuta(
    val ejecutado:Boolean? = false,
    val usuario: Usuario? = null,
    val marca: LocalDateTime? = null,
    val log:String? = ""

): Serializable{}

data class Conexion(
    val estacion:Estacion? = null,
    val oLT:String? = "",
    val pON:String? = "",
    val vLAN:String? = "",
    ):Serializable{}

data class Ftth(
    val conexion: Conexion? = null,
    val nAPCP:String? = "",
    val potenciaIn:String? = "",
    val portenciaOut:String? ="",
    val spliter:String? = ""
): Serializable
{}

data class Eoc(
    val conexion: Conexion? = null,
    val nodo:String? = "",
    val master:String? = "",
    val atenuacion:String = "",
    val snr:String? = "" //señal ruido
): Serializable{}

data class Inalambrico(
    val conexion: Conexion? = null,

): Serializable{}

