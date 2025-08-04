package com.hjsolutions.isp_api.service.dto

import java.io.Serializable

data class InfoSaldoFavorDTO(
    var id:Int = 0,
    var cajero:String = "",
    var valor : String = "",
    var detalle : String = "",
    var marca: String = "",
    var medioPago : String  ="",
    var tipo : String = ""

):Serializable
{
    override fun toString(): String {
        return ""
    }
}