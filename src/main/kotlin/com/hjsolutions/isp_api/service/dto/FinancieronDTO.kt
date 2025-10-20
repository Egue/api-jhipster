package com.hjsolutions.isp_api.service.dto


import java.io.Serializable

data class InfoFinancieroDTO(
    var id:String = "",
    var usuario:String = "",
    var fecha:String = "",
    var comentario:String="",
    var valor_base:String = "",
    var valor_iva :String = "",
    var nc :String = "",
    var text: String = ""

):Serializable
{
 override fun toString(): String {
        return ""
    }
}