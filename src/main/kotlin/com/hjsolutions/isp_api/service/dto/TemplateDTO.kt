package com.hjsolutions.isp_api.service.dto

import com.hjsolutions.isp_api.domain.Consecutivo
import com.hjsolutions.isp_api.domain.Templates
import java.io.Serializable

data class TemplateDTO(
    var id:String?="",
    var name:String? ="",
    var idServicio:Number?=0,
    var template:String?="",
    var consecutivoA: ConsecutivoDTO? = null,
    var consecutivoB: ConsecutivoDTO? = null
): Serializable

data class ConsecutivoDTO(
    var consecutivo:Number  =0,
    var prefijo:String = ""
): Serializable
