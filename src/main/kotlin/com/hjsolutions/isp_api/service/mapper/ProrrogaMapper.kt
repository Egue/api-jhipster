package com.hjsolutions.isp_api.service.mapper
import com.hjsolutions.isp_api.domain.Prorroga
import com.hjsolutions.isp_api.service.dto.ProrrogaDTO

fun ProrrogaDTO.toEntity(): Prorroga = Prorroga(
     idContrato = idContrato,
     createBy = createBy,
     fechaProrroga = fechaProrroga

)

 