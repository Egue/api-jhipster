package com.hjsolutions.isp_api.service

import com.comunicamosmas.api.domain.Contrato
import com.comunicamosmas.api.repository.IContratoDao
import com.hjsolutions.isp_api.domain.Consecutivo
import com.hjsolutions.isp_api.domain.Templates
import com.hjsolutions.isp_api.repository.TemplateRepository
import com.hjsolutions.isp_api.service.dto.TemplateDTO
import com.hjsolutions.isp_api.service.mapper.toEntity
import com.hjsolutions.isp_api.service.mapper.toTDO
import org.springframework.stereotype.Service

@Service
class TemplateService(private val templateResitory: TemplateRepository , private val iContratoDao: IContratoDao) {

    fun save(templateDTO: TemplateDTO):Templates{

        val entity : Templates = templateDTO.toEntity()
        return templateResitory.save(entity)
    }

    fun updated(templateDTO: TemplateDTO): Templates{

        val template = templateResitory.findById(templateDTO.id)
            .orElseThrow { IllegalArgumentException("Template with id ${templateDTO.id} does not exist") }

        val update = template.copy(
            name = templateDTO.name,
            template = templateDTO.template,
            idServicio = template.idServicio,
            consecutivoA = template.consecutivoA,
            consecutivoB = template.consecutivoB
        )

        return templateResitory.save(template)

    }

    fun findOneNameAndIdService(name:String, idService:Number): TemplateDTO?{

        val template : Templates? =  templateResitory.findOneByIdServicioAndName(idServicio = idService , name = name)
        val templateDTO : TemplateDTO? = template?.let { it.toTDO() }
        return templateDTO
    }


    fun generateConsecutivo( contrato:String , name:String):Long{

        var contrato : Contrato = iContratoDao.findById(contrato.toLong()).get() ?: throw Exception("no existe el contrato")
        val templates : Templates? = templateResitory.findOneByIdServicioAndName(contrato.idServicio , name) ?: throw Exception("no existe el template")
        val nuevoConsecutivo : Long = when (contrato.grupo){
            "A" -> {
                val actual = templates?.consecutivoA?.consecutivo?.toLong() ?: throw Exception("el consecutivoA no existe")
                actual  + 1
            }
            "B" -> {
                val actual = templates?.consecutivoB?.consecutivo?.toLong() ?: throw Exception("el consecutivoB no existe")
                actual + 1
            }
            else -> throw Exception("Origen invalido (solo A o B)")
        }
        val prefijo:String;
        if (contrato.grupo == "A") {
            templates.consecutivoA = templates.consecutivoA!!.copy(
                consecutivo = nuevoConsecutivo
            )
            prefijo = templates.consecutivoA?.prefijo + nuevoConsecutivo
        } else {
            templates.consecutivoB = templates.consecutivoB!!.copy(
                consecutivo = nuevoConsecutivo
            )
            prefijo = templates.consecutivoB?.prefijo + nuevoConsecutivo
        }
        templateResitory.save(templates)
        //actualizaqr contrato
        contrato.fisico = prefijo
        iContratoDao.save(contrato)
        //actualizar contrato
        return nuevoConsecutivo
    }

}
