package com.hjsolutions.isp_api.service

import com.hjsolutions.isp_api.domain.Inventory
import com.hjsolutions.isp_api.repository.InventoryRepository
import com.hjsolutions.isp_api.service.mapper.toEntity
import com.hjsolutions.isp_api.service.mapper.toDTO
import com.hjsolutions.isp_api.service.dto.InventoryDTO
import org.springframework.stereotype.Service

@Service
class InventoryService(
    private val inventoryRepository: InventoryRepository
) {

    fun findAll(): List<InventoryDTO> {

        return inventoryRepository.findAll().map { it.toDTO() }
    }

    fun save(inventoryDTO: InventoryDTO): Inventory {

        val findInventory: Inventory? = inventoryRepository.findByHostname(inventoryDTO.hostname)

        findInventory?.let {
            throw Exception("Inventory with hostname ${inventoryDTO.hostname} already exists")
        }
        
        val entity: Inventory = inventoryDTO.toEntity()

        return inventoryRepository.save(entity)
    }
 
}