package com.hjsolutions.isp_api.service.mapper

import com.hjsolutions.isp_api.domain.Templates
import com.hjsolutions.isp_api.service.dto.TemplateDTO

fun TemplateDTO.toEntity(): Templates = Templates(
    name = name,
    idServicio = idServicio,
    template = template
)
