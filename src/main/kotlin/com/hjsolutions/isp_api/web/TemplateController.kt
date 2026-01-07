package com.hjsolutions.isp_api.web

import com.hjsolutions.isp_api.domain.Templates
import com.hjsolutions.isp_api.service.ContratoDigitalService
import com.hjsolutions.isp_api.service.TemplateService
import com.hjsolutions.isp_api.service.dto.TemplateDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/kt")
class TemplateController(private val templateService: TemplateService , private val contratoDigitalService: ContratoDigitalService) {

    @PostMapping("/template")
    fun save(@RequestBody template: TemplateDTO): ResponseEntity<Templates> {
        val savedTemplate: Templates;
        if(template.id?.isNotEmpty() == true){
                //actualizar
            savedTemplate = templateService.updated(template)
        }else{
             savedTemplate = templateService.save(template)
        }

        return ResponseEntity.created(URI("/api/kt/template/${savedTemplate.name}"))
            .body(savedTemplate)
    }

    @GetMapping("/template")
    fun getTemplateOne(@RequestParam("name") name:String ,  @RequestParam("idServicio") idServicio:String): ResponseEntity<TemplateDTO?>{
        val findOneTemplate = templateService.findOneNameAndIdService(name = name , idService = idServicio.toInt())

        return ResponseEntity.ok()
            .body(findOneTemplate)
    }

    @GetMapping("/template/contrato")
   suspend fun getTemplateContrato(@RequestParam("contrato") contrato:String):ResponseEntity<Any>{
        try {

            val response = contratoDigitalService.templateAndInfoContrato(contrato.toLong())
            return ResponseEntity.ok().body(response)
        }catch (e: Exception){
            val response: HashMap<String, String> = HashMap()
            response.put("error" , e.message.toString())
            return ResponseEntity.badRequest().body(response)
        }
    }

    @GetMapping("template/consecutivo")
    suspend fun generateConsecutiv(
        @RequestParam("contrato")contrato:String,
        @RequestParam("name")name:String): ResponseEntity<Any>{

        try {
            val response : HashMap<String, String> = HashMap()
            val consecutivo = templateService.generateConsecutivo(contrato , name)
            response.put("consecutivo" , consecutivo.toString())
            return ResponseEntity.ok().body(response)
        }catch (e: Exception){
            val response: HashMap<String, String> = HashMap()
            response.put("error" , e.message.toString())
            return ResponseEntity.badRequest().body(response)
        }
    }
}
