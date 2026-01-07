package com.hjsolutions.isp_api.service.mapper

import com.hjsolutions.isp_api.domain.Consecutivo
import com.hjsolutions.isp_api.domain.Templates
import com.hjsolutions.isp_api.service.dto.ConsecutivoDTO
import com.hjsolutions.isp_api.service.dto.TemplateDTO


fun Templates.toTDO(): TemplateDTO = TemplateDTO(
    id = id,
    name = name,
    idServicio = idServicio,
    template = template,
    consecutivoA = consecutivoA?.let{ConsecutivoDTO(it.consecutivo , it.prefijo)},
    consecutivoB = consecutivoB?.let{ ConsecutivoDTO(it.consecutivo , it.prefijo)}

)

fun TemplateDTO.toEntity(): Templates = Templates(
    name = name,
    idServicio = idServicio,
    template = template,
    consecutivoA = consecutivoA?.let { Consecutivo(it.prefijo , it.consecutivo) },
    consecutivoB = consecutivoB?.let { Consecutivo(it.prefijo , it.consecutivo) }
)
