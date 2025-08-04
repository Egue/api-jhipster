package com.hjsolutions.isp_api.domain

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import liquibase.structure.core.Column
import liquibase.pro.packaged.id
import java.time.Instant

@Document(collection="puntos_accesos")
data class PuntosAccesos(
    @Id
    val id:ObjectId? = null,
    @Field("CODIGO")
    val codigo:Number = 0,
    @Field("COD_MUNICIPIO")
    val codMunicipio:Number = 0,
    @Field("NOMBRE")
    val nombre:String = "",
    @Field("IP")
    val ip:String = "",
    @Field("ESTADO")
    val estado:String = "",
    @Field("PUERTO")
    val puerto:Number = 0,
    @Field("USUARIO_TORRE")
    val usuarioTorre:String = "",
    @Field("CLAVE_TORRE")
    val claveTorre:String = "",
    @Field("GESTION_USUARIOS")
    val gestionUsuarios:String = "",
    @Field("LOCAL_ADDRESS")
    val localAddress:String = ""
){}

@Document(collection = "suscripciones")
data class Suscripcion(
    @Id
    @Field("_id")
    val id: ObjectId? = null,
    @Field("CODIGO")
    val codigo: Int?= 0,
    @Field("FECHA_CREACION")
    val fechaCreacion: String? = "",
    @Field("REFERENCIA")
    val referencia: Number? = 0,
    @Field("IDENTIFICACION")
    val identificacion: Number? = 0,
    @Field("COD_MUNICIPIO_INSTALACION")
    val codMunicipioInstalacion : Int? = 0,
    @Field("COD_BARRIO_INSTALACION")
    val codBarrioInstalacion: Int? = 0,
    @Field("DIR_INSTALACION")
    val dirInstalacion: String? = "",
    @Field("COD_MUNICIPIO_CORRESPONDENCIA")
    val codMunicipioCorrespondencia: Int? = 0,
    @Field("COD_BARRIO_CORRESPONDENCIA")
    val codBarrioCorrespondencia: Int? = 0,
    @Field("DIR_CORRESPONDENCIA")
    val dirCorrespondencia: String? = "",
    @Field("TIPO_VIVIENDA")
    val tipoVivienda: String? = "",
    @Field("ESTRATO")
    val estrato: Int? = 0,
    @Field("ESTADO")
    val estado: String? = "",
    @Field("FECHA_ESTADO")
    val fechaEstado: String?  =""
){}

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
    @Field("CODIGO")
    var codigp:Number?  =0,
    @Field("COD_SERVICIO")
    var codServicio:Number? = 0,
    @Field("COD_PERFIL")
    var codPerfil : Number? = 0,
    @Field("DESCRIPCION")
    var descripcion: String? = "",
    @Field("PRECIO")
    var precio: Number? = 0,
    @Field("IVA")
    var iva : Number? = 0,
    @Field("ESTADO")
    var estado: String? = "",
    @Field("VALOR_DERIVACION")
    var valorDerivacion: Number? = 0,
    @Field("COD_CONCEPTO")
    var codConcepto: Number? = 0,
    @Field("PREPAGO")
    var prepago:Number? = 0,
    var contenido_paquete_venta:List<ContenidoVenta>? = emptyList()
){}


@Document(collection="contenido_paquete_venta")
data class ContenidoVenta(
    @Id
    var id:ObjectId? = null,
    @Field("CODIGO")
    var codigo : Number = 0,
    @Field("COD_SERVICIO")
    var codServicio:Number = 0,
    @Field("COD_PAQUETE_VENTA")
    var codPaqueteVenta:Number = 0,
    @Field("COD_PAQUETE")
    var codPaquete:Number = 0,
    @Field("COD_CANAL")
    var codCanal:Number = 0,
    @Field("COD_VELOCIDAD")
    var codVelocidad:Number = 0,
    var velocidad:Velocidades? = null
){}

@Document(collection="velocidades")
data class Velocidades(
    @Id
    var id:ObjectId? = null,
    @Field("CODIGO")
    var codigo:Number  = 0,
    @Field("NOMBRE")
    var nombre:String = "",
    @Field("PERFIL")
    var perfil:String? = "",
    @Field("TIMPO_LIMITE_INACTIVIDAD")
    var tiempoLimiteInactividad:String ="",
    @Field("TIEMPO_LIMITE_CONEXION")
    var tiempoLimiteConexion:String = "",
    @Field("ACTUALIZACION_AUTOMATICA")
    var actualizacionAutomatica:String = "",
    @Field("NUMERO_CLIENTES_COMPARTIR")
    var numeroClientesCompartir:Number = 0,
    @Field("VELOCIDA_LIMITE_SUBIDA")
    var velocidadLimiteSubida:Number = 0,
    @Field("VELOCIDAD_LIMITE_DESCARGA")
    var velocidadLimiteDescarga:Number = 0 ,
    @Field("ESTADO")
    var estado:String = ""
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
    var cantIp:String  ="",
    var suscripcion : Suscripcion?,
    var paquete_venta: PaquetesVenta?
){}
