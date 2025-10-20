package com.hjsolutions.agente_inventory.api.web
import com.hjsolutions.agente_inventory.api.service.AgenteService
import com.hjsolutions.agente_inventory.api.domain.Inventory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class AgenteController(
    private val agenteService: AgenteService
) {

    @GetMapping("/inventory")
    fun getAllAgentes(): ResponseEntity<Inventory> {

        var response = agenteService.generateQuery()
        
        return ResponseEntity.ok(response)
    }
 
}