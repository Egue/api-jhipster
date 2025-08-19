package com.hjsolutions.isp_api.web

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping 
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.GetMapping
import com.hjsolutions.isp_api.service.ContratoSaldoFavorService
import com.hjsolutions.isp_api.service.dto.InfoSaldoFavorDTO

import org.springframework.http.ResponseEntity
@RestController
@RequestMapping("/api/kt")
class ContratoSaldoFavorController( private val saldoFavorService:ContratoSaldoFavorService)
{


    @GetMapping("/saldofavor/info")
    fun get_saldo_favor_info(@RequestParam("id") id : Long):ResponseEntity<List<InfoSaldoFavorDTO>>
    {
        var list = saldoFavorService.get_info_saldo_favor(id)

        return ResponseEntity.ok().body(list)
    }
}