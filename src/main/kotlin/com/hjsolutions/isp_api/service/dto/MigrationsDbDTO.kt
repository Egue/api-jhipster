package com.hjsolutions.isp_api.service.dto
import java.io.Serializable
import com.opencsv.bean.CsvBindByName

data class ClienteCablemagDTO(
    @field:CsvBindByName(column = "IDENTIFICACION")
    var identificacion:Int = 0,
    @field:CsvBindByName(column = "TIPO_IDENTIFICACION")
    var tipo_identificacion:String = "",
    @field:CsvBindByName(column = "DV")
    var dv:String = "",
    @field:CsvBindByName(column = "SEXO")
    var sexo:String = "",
    @field:CsvBindByName(column = "ESTADO_CIVIL")
    var estado_civil:String = "",
    @field:CsvBindByName(column = "COD_MUNICIPIO_EXPEDICION_CEDULA")
    var cod_municipio:String="",
    @field:CsvBindByName(column = "FECHA_EXPEDICION_CEDULA")
    var fecha_expedicion_cedula:String="",
    @field:CsvBindByName(column = "NOMBRE")
    var nombre:String = "",
    @field:CsvBindByName(column = "APELLIDOS")
    var apellidos:String ="",
    @field:CsvBindByName(column = "FECHA_NACIMIENTO")
    var fecha_nacimiento:String="",
    @field:CsvBindByName(column = "COD_MUNICIPIO_DOMICILIO")
    var cod_municipio_domicilio:String="",
    @field:CsvBindByName(column = "COD_BARRIO_DOMICILIO")
    var cod_barrio_domicilio:String="",
    @field:CsvBindByName(column = "DIRECCION_DOMICILIO")
    var direccion_domicilio:String="",
    @field:CsvBindByName(column = "PUNTO_REFERENCIA_DOMICILIO")
    var punto_referencia_domicilio:String="",
    @field:CsvBindByName(column = "INDICATIVO1")
    var indicativo1:String="",
    @field:CsvBindByName(column = "TELEFONO1")
    var telefono1:String="",
    @field:CsvBindByName(column = "EXTENCION1")
    var extension1:String="",
    @field:CsvBindByName(column = "INDICATIVO2")
    var indicativo2:String="",
    @field:CsvBindByName(column = "TELEFONO2")
    var telefono2:String="",
    @field:CsvBindByName(column = "EXTENCION2")
    var extension2:String="",
    @field:CsvBindByName(column = "MOVIL")
    var movil:String  ="",
    @field:CsvBindByName(column = "EMAIL")
    var email:String="",
    @field:CsvBindByName(column = "ACTIVIDAD_ECONOMICA")
    var actividad_economica:String="",
    @field:CsvBindByName(column = "EMPRESA")
    var empresa:String="",
    @field:CsvBindByName(column = "COD_MUNICIPIO_EMPRESA")
    var cod_municipio_empresa:String="",
    @field:CsvBindByName(column = "COD_BARRIO_EMPRESA")
    var cod_barrio_empresa:String="",
    @field:CsvBindByName(column = "DIRECCION_EMPRESA")
    var cod_direccion_empresa:String="",
    @field:CsvBindByName(column = "INDICATIVO_EMPRESA")
    var indicativo_empresa:String="",
    @field:CsvBindByName(column = "TELEFONO_EMPRESA")
    var telefono_empresa:String="",
    @field:CsvBindByName(column = "EXTENCION_EMPRESA")
    var extension_empresa:String="",
    @field:CsvBindByName(column = "NOMBRE_COMPLETO_REFERENCIA")
    var nombre_completo_referencia:String="",
    @field:CsvBindByName(column = "COD_MUNICIPIO_REFERENCIA")
    var cod_municipio_referencia:String="",
    @field:CsvBindByName(column = "COD_BARRIO_REFERENCIA")
    var cod_barrio_referencia:String="",
    @field:CsvBindByName(column = "DIRECCION_REFERENCIA")
    var direccion_referencia:String="",
    @field:CsvBindByName(column = "INDICATIVO_REFERENCIA")
    var indicativo_referencia:String="",
    @field:CsvBindByName(column = "TELEFONO_REFERENCIA")
    var telefono_referencia:String="",
    @field:CsvBindByName(column = "EXTENCION_REFERENCIA")
    var extension_referencia:String="",
    @field:CsvBindByName(column = "MOVIL_REFERENCIA")
    var movil_referencia:String="",
    @field:CsvBindByName(column = "MOVIL_TRASLADO")
    var movil_traslado:String="",
    @field:CsvBindByName(column = "DIGITO_VERIFICACION")
    var digito_verificacion:String="",
    @field:CsvBindByName(column = "NOMBRE_REPRESENTANTE")
    var nombre_representante:String="",
    @field:CsvBindByName(column = "IDENTIFICACION_REPRESENTANTE")
    var identificacion_representante:String=""
    @field:CsvBindByName(column = "FECHA")
    var fecha:String=""

):Serializable{

}