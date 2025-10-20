package com.hjsolutions.isp_api.web

import com.hjsolutions.isp_api.service.ContratoDigitalService
import kotlinx.coroutines.CoroutineScope
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import kotlin.coroutines.suspendCoroutine

@RestController
@RequestMapping("/api/kt")
class ContratosDigitales(private val contratoDigitalService: ContratoDigitalService) {

    @GetMapping("/digital")
    suspend fun sincroniceAppWrite(@RequestParam("idContrato") idContrato:String): ResponseEntity<Any>{

        try {

            contratoDigitalService.sincronice(idContrato.toLong())

            return ResponseEntity.ok().body("ok")
        }catch (e: Exception){
            return ResponseEntity.badRequest().body(e.message)
        }
    }
}
