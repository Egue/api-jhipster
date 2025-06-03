package com.hjsolutions.isp_api.web

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.http.ResponseEntity
import com.hjsolutions.isp_api.service.ServicioService

@RestController
@RequestMapping("/api/kt/servicio")
class ServicioController(
    private val servicioService:ServicioService
){

    /*cortar servicios */

    @GetMapping("/cortar")
    fun cortarServicio(@RequestParam("cus") cus:Long){

        //find contrato estacion
        var servicio  = servicioService.cortarCliente(cus)
    }

    @GetMapping("/onu/unconfigured")
    fun getUnconfiguredOnus(@RequestParam("id_estacion") idEstacion:Long): ResponseEntity<String> {

        //find estacion
        var response = servicioService.get_unconfigured_onus(idEstacion)

        return ResponseEntity.ok(response)
    }

}