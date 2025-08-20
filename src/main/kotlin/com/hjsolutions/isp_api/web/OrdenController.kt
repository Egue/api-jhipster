package com.hjsolutions.isp_api.web

import com.hjsolutions.isp_api.service.OrdenesService
import com.hjsolutions.isp_api.service.dto.OrdenesDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
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

}
