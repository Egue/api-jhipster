package com.hjsolutions.isp_api.web

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.http.ResponseEntity
import com.hjsolutions.isp_api.service.dto.InfoFinancieroDTO
import com.hjsolutions.isp_api.service.FinancieroService
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/kt")
class FinancieroController(
    private val financieroService:FinancieroService
)
{

    @GetMapping("/financiero/info")
    fun get_financieron_info(@RequestParam("idNc") idNc:Long):ResponseEntity<List<InfoFinancieroDTO>>
    {
        var resp  = financieroService.get_info_nc(idNc)


        return ResponseEntity.ok().body(resp)
    }
}