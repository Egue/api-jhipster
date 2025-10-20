package com.hjsolutions.isp_api.service.dto


import java.io.Serializable

data class ConfigurationOLT(
    var type:String? = "",
    var endpoint: List<EndPointOLT?> = emptyList()
) : Serializable {
    override fun toString(): String {
        return "ConfigurationOLT(type='${type}', endpoint='${endpoint.joinToString(", ") { it?.toString() ?: "null" }}')"
    }
}

data class EndPointOLT(
    var name: String? = "",
    var endpoint: String? = ""
) : Serializable {
    override fun toString(): String {
        return "EndPoint(url='${name}', token='******')"
    }   
}