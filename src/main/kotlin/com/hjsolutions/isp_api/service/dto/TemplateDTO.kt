package com.hjsolutions.isp_api.service.dto

import com.hjsolutions.isp_api.domain.Templates
import java.io.Serializable

data class TemplateDTO(
    var id:String?="",
    var name:String? ="",
    var idServicio:Number?=0,
    var template:String?=""
): Serializable{
    constructor(template: Templates): this(id = template.id , name = template.name , idServicio=template.idServicio, template = template.template)
}
