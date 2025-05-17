package com.hjsolutions.isp_api.web
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController 
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import com.hjsolutions.isp_api.service.dto.IptvDTO
import com.hjsolutions.isp_api.domain.Iptv
import com.hjsolutions.isp_api.service.IptvService

import java.net.URI

@RestController
@RequestMapping("/api/kt")
class IptvController(
    private val iptvService: IptvService
) {
    @GetMapping("/iptv")
    fun getIptv(): ResponseEntity<List<Iptv>> {
        val iptvList = iptvService.findAll()
        return if (iptvList.isNotEmpty()) {
            ResponseEntity.ok(iptvList)
        } else {
            ResponseEntity.status(HttpStatus.NO_CONTENT).body(emptyList())
        } 
    }

    @PostMapping("/iptv")
    fun createIptv(@RequestBody iptvDTO: IptvDTO): ResponseEntity<Iptv> {

        val iptv = iptvService.save(iptvDTO)

        return ResponseEntity.created(URI("/api/kt/iptv/${iptv.id}"))
            .body(iptv)
    }

    @PutMapping("/iptv/{id}")
    fun updateIptv(@PathVariable id: String, @RequestBody data: String): ResponseEntity<String> {
        return ResponseEntity.ok("IPTV with ID $id updated with data: $data")
    }

    @DeleteMapping("/iptv/{id}")
    fun deleteIptv(@PathVariable id: String): ResponseEntity<String> {
        return ResponseEntity.ok("IPTV with ID $id deleted")
    }
}