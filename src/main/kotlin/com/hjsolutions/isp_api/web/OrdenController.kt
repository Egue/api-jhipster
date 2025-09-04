package com.hjsolutions.isp_api.web

import com.comunicamosmas.api.domain.Orden
import com.hjsolutions.isp_api.service.OrdenesService
import com.hjsolutions.isp_api.service.dto.OrdenesDTO
import com.hjsolutions.isp_api.service.dto.createDTO
import liquibase.pro.packaged.cr
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/kt")
class OrdenController(private val ordenService: OrdenesService) {

    @GetMapping("ordenes")
    fun ordenes(@RequestParam("servicio") servicio:Long, @RequestParam("type") type:Long): ResponseEntity<List<OrdenesDTO>>{

        val list:List<OrdenesDTO> = ordenService.ordenes(servicio , type)

        return ResponseEntity.ok(list)
    }

    @PostMapping("ordenes/create")
    fun create(@RequestBody createDTO: createDTO): ResponseEntity<Any>{
        try {
            ordenService.createOrden(createDTO)

            return ResponseEntity.ok().build()
        }catch (e: Exception)
        {
            return ResponseEntity.badRequest().body(mapOf(
                "error" to "Error procesando",
                "detail" to e.message
            ))
        }
    }

    @GetMapping("ordenes/corte_masivamente")
    fun masivamente_corte(@RequestParam("idServicio") idServicio:String , @RequestParam("idUser") user:String): ResponseEntity<Any>{
        try {
            val list = ordenService.generateOrdenMasivadeCortes(idServicio.toLong() , user.toLong())
            return ResponseEntity.ok().body(list)
        }catch (e: Exception){
            return ResponseEntity.badRequest().body(e.message)
        }
    }

}
