package com.hjsolutions.isp_api.service.dto

import java.time.LocalDateTime

import java.io.Serializable

data class ProrrogaDTO(
      var idContrato:Long?  = 0,
      var createBy:Long? = 0,
      var fechaProrroga: String? = "",
      var state: String? = "A"
):Serializable{}

data class ListProrrogaDTO(
      var dataProrrogaListService: List<DataProrrogaByServiceDTO> = emptyList(),
      var prorrogaList : List<ProrrogaInfoDTO>
):Serializable
{

}

data class DataProrrogaByServiceDTO(
      var servicio:String? = "",
      var amount : Int? = 0,
      var saldo : Double?=0.0
):Serializable{}

data class ProrrogaInfoDTO(
    var id:String?="",
      var idContrato:Int? =0,
      var tipo:String? = "",
      var nameCliente:String?= "",
      var servicio:String?="",
      var direccion:String?="",
      var parcial:Double?=0.0,
      var total:Double?=0.0,
      var fechaProrroga:String?=""

):Serializable{}
