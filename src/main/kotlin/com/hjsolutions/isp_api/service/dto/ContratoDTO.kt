package com.hjsolutions.isp_api.service.dto



data class ContratoInfo(
    val cliente:ClienteContrato,
    val contrato:DatosContrato,
    val tarifa:TarifaContrato,
    val clausula:ClausulaContrato,
    val vendedor: VendedorContrato
)
data class ClienteContrato(
    val id_cliente:Long,
    val nombre_cliente:String,
    val tipo_cliente:String,
    val documento_cliente:String,
    val correo_cliente:String,
    val numeros_cliente:String
)
data class DatosContrato(
    val id_contrato:Long,
    val estrato:Long,
    val consecutivo:String,
    val origen:String,
    val observacion:String,
    val direccionServicio:DireccionContrato,
    val direccionResidencia: DireccionContrato,
    val valor_instalacion: Number,
    val valor_reconexion : Number,
    val valor_traslado : Number
){}
data class TypeService(
    val type:String,
    val repHtml:String
){}
data class DireccionContrato(
    val departamento:String,
    val municipio:String,
    val barrio:String,
    val nomenclatura:String
){}
data class TarifaContrato(
    val valor: Double,
    val tecnologia:String,
    val velocidad:String
){}

data class  ClausulaContrato(
    val conexion:Number,
    val mes_1:Number,
    val mes_2:Number,
    val mes_3:Number,
    val mes_4:Number,
    val mes_5:Number,
    val mes_6:Number,
    val mes_7:Number,
    val mes_8:Number,
    val mes_9:Number,
    val mes_10:Number,
    val mes_11:Number,
    val mes_12:Number
){}

data class VendedorContrato(
    val nombre:String
){}
