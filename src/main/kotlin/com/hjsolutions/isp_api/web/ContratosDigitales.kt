package com.hjsolutions.isp_api.web

import com.hjsolutions.isp_api.service.ContratoDigitalService
import io.appwrite.models.DocumentList
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

            return ResponseEntity.ok().build()
        }catch (e: Exception){
            return ResponseEntity.badRequest().body(e.message)
        }
    }

    @GetMapping("appWrite/contrato")
    suspend fun getContratoAppWrite(@RequestParam("contrato") contrato:String): ResponseEntity<DocumentList<Map<String,Any>>>?{
        try {
            val response = contratoDigitalService.findContratoAppwrite(contrato)
            return ResponseEntity.ok().body(response)
        }catch (e: Exception){
            e.printStackTrace()
            return ResponseEntity.badRequest().build()
        }
    }



    @GetMapping("/find")
    suspend fun find(@RequestParam("contrato") contrato:String):ResponseEntity<Any>{

        try {
            val response = contratoDigitalService.findContratoByImplementacion(contrato)

            return ResponseEntity.ok().body(response)

        }catch (e: Exception){
            return ResponseEntity.badRequest().body(e.message)
        }
    }
}
