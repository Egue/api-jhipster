package com.hjsolutions.isp_api.web
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.server.ResponseStatusException
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import com.hjsolutions.isp_api.domain.Inventory
import com.hjsolutions.isp_api.service.InventoryService
import com.hjsolutions.isp_api.service.dto.InventoryDTO
import java.net.URI

@RestController
@RequestMapping("/api/kt")
class InventoryController(
    private val inventoryService: InventoryService
) {
    @GetMapping("/inventory/computer")
    fun getInventory(): ResponseEntity<List<InventoryDTO>> {
        val inventoryList = inventoryService.findAll()
        return if (inventoryList.isNotEmpty()) {
            ResponseEntity.ok(inventoryList)
        } else {
            ResponseEntity.status(HttpStatus.NO_CONTENT).body(emptyList())
        }
    }

    @PostMapping("/inventory")
    fun createInventory(@RequestBody inventory: InventoryDTO): ResponseEntity<Inventory> {
        var savedInventory = inventoryService.save(inventory)
        return ResponseEntity.created(URI("/api/kt/inventory/${savedInventory.hostname}"))
            .body(savedInventory)
    }

    /*@PutMapping("/inventory/{hostname}")
    fun updateInventory(@PathVariable hostname: String, @RequestBody inventory: Inventory): ResponseEntity<Inventory> {
        val updatedInventory = inventoryService.update(hostname, inventory)
        return ResponseEntity.ok(updatedInventory)
    }

    @DeleteMapping("/inventory/{hostname}")
    fun deleteInventory(@PathVariable hostname: String): ResponseEntity<String> {
        val deleted = inventoryService.deleteByHostname(hostname)
        return if (deleted) {
            ResponseEntity.ok("Inventory with hostname $hostname deleted")
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory with hostname $hostname not found")
        }
    }*/
}