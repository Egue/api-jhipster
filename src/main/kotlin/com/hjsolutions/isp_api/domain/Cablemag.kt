package com.hjsolutions.isp_api.domain

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import liquibase.structure.core.Column
import java.time.Instant
@Document(collection = "suscripciones")
data class Suscripciones(
    @Id
    val id: ObjectId? = null,
    val CODIGO: Int,
    val FECHA_CREACION: Instant,
    val REFERENCIA: Long,
    val IDENTIFICACION: Long,
    val COD_MUNICIPIO_INSTALACION: Int,
    val COD_BARRIO_INSTALACION: Int,
    val DIR_INSTALACION: String,
    val COD_MUNICIPIO_CORRESPONDENCIA: Int,
    val COD_BARRIO_CORRESPONDENCIA: Int,
    val DIR_CORRESPONDENCIA: String,
    val TIPO_VIVIENDA: String,
    val ESTRATO: Int,
    val ESTADO: String,
    val FECHA_ESTADO: Instant
)

@Document(collection = "barrios")
data class Barrios(       
    @Id
    var id: ObjectId? = null,
    var COD_MUNICIPIO: Number = 0, 
    var COD_PUNTO_ACCESO: Number = 0,
    var NOMBRE: String ="",
    var ESTADO: String =""
) {
    // Additional methods or validations can be added here if needed
}

@Document(collection="paquetes_venta")
data class PaquetesVenta(
    @Id
    var id:ObjectId? = null,
    var CODIGO:Number  =0,
    var COD_SERVICIO:Number = 0,
    var COD_PERFIL : Number = 0,
    var DESCRIPCION: String = "",
    var PRECIO: Number = 0,
    var IVA : Number = 0,
    var ESTADO: String = "",
    var VALOR_DERIVACION: Number = 0,
    var COD_CONCEPTO: Number = 0,
    var PREPAGO:Number = 0
){}

@Document(collection="perfiles")
data class Perfiles(
    @Id
    var id:ObjectId? = null,
    var CODIGO: Number = 0,
    var NOMBRE: String = "",
    var ESTADO :String = "",
    @Field("COD_SERVICIO")
    var codServicio : Number = 0,
    var NUM_DERIVACIONES :Number = 0,
    var COD_TIPO_SERVICIO: Number = 0
){}

@Document(collection="equipos_asignados")
data class EquiposAsignados(
    @Id
    @Field("_id")
    var id:ObjectId? = null,
    @Field("NUMERO")
    var numero:Number = 0,
    @Field("FECHA_ASIGNACION")
    var fechaAsignacion:String?="",
    @Field("COD_SUSCRIPCION")
    var codSuscripcion:Number = 0,
    @Field("COD_ORDEN_SERVICIO")
    var codOrdenServicio: Number = 0,
    @Field("COD_PAQUETE_VENTA")
    var codPaqueteVenta:Number = 0,
    @Field("TIPO")
    var tipo:String = "",
    @Field("PRINCIPAL")
    var principal :String="",
    @Field("PROPIETARIO")
    var propietario:String="",
    @Field("ANTIGUEDAD")
    var antiguedad:String="",
    @Field("COD_SERVICIO")
    var codServicio:Number = 0,
    @Field("FECHA_INICIO")
    var fechaInicio:String?="",
    @Field("FECHA_FIN")
    var fechaFin:String?="",
    @Field("DESCUENTO")
    var descuento:Number = 0,
    @Field("COD_PAQUETE_VENTA_PROGRAMADO")
    var codPaqueteVentaProgramado:Number = 0,
    @Field("FECHA_CAMBIO")
    var fechaCambio:String?="",
    @Field("ACTIVO")
    var activo:String = "",
    @Field("CODIGO")
    var codigo:Number  = 0,
    @Field("FECHA_INICIO_EQUIPO")
    var fechaInicioEquipo:String?="",
    @Field("FECHA_FIN_EQUIPO")
    var fechaFinEquipo:String?="",
    @Field("IP_PUBLICA")
    var ipPublica:String = "",
    @Field("ID_TAREA")
    var idTarea:Number  = 0,
    @Field("COD_AP")
    var codAp:Number = 0,
    @Field("USUARIO")
    var usuario:String ="",
    @Field("CLAVE")
    var clave:String = "",
    @Field("COMENTARIO")
    var comentario:String  ="",
    @Field("CORTESIA")
    var cortesia:String  ="",
    @Field("LEVANTAMIENTO")
    var levantamiento:String = "",
    @Field("DESCUENTO_PROGRAMADO")
    var descuentoProgramado:String = "",
    @Field("DTO_ESPECIAL")
    var dtoEspecial:String  ="",
    @Field("CANT_IP")
    var cantIp:String  =""
){}