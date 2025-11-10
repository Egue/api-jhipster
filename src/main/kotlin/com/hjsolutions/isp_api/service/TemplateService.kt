package com.hjsolutions.isp_api.service

import com.hjsolutions.isp_api.domain.Templates
import com.hjsolutions.isp_api.repository.TemplateRepository
import com.hjsolutions.isp_api.service.dto.TemplateDTO
import com.hjsolutions.isp_api.service.mapper.toEntity
import org.springframework.stereotype.Service

@Service
class TemplateService(private val templateResitory: TemplateRepository) {

    fun save(templateDTO: TemplateDTO):Templates{

        val entity : Templates = templateDTO.toEntity()
        return templateResitory.save(entity)
    }

    fun findOneNameAndIdService(name:String, idService:Number): Templates?{

        return templateResitory.findOneByIdServicioAndName(idServicio = idService , name = name)
    }
}
