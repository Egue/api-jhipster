package com.hjsolutions.isp_api.service.mapper

import com.hjsolutions.isp_api.domain.Inventory
import com.hjsolutions.isp_api.domain.Os
import com.hjsolutions.isp_api.domain.Processor
import com.hjsolutions.isp_api.domain.Memory
import com.hjsolutions.isp_api.domain.Disk
import com.hjsolutions.isp_api.domain.Network
import com.hjsolutions.isp_api.domain.Manufacturer
import com.hjsolutions.isp_api.service.dto.InventoryDTO
import com.hjsolutions.isp_api.service.dto.OsDTO
import com.hjsolutions.isp_api.service.dto.ProcessorDTO
import com.hjsolutions.isp_api.service.dto.MemoryDTO
import com.hjsolutions.isp_api.service.dto.DiskDTO
import com.hjsolutions.isp_api.service.dto.NetworkDTO
import com.hjsolutions.isp_api.service.dto.ManufacturerDTO

fun Inventory.toDTO(): InventoryDTO = InventoryDTO(
    hostname = hostname,
    serialNumber = serialNumber, 
    location = location,
    asignedTo = asignedTo,
    os = os?.let { OsDTO(it.family, it.version, it.arch) },
    processor = processor?.let { ProcessorDTO(it.name, it.identifier, it.vendor, it.frequency.toLong(), it.cores) },
    memory = memory?.let { MemoryDTO(it.total, it.available, it.used) },
    disk = disk?.let { DiskDTO(it.total.toLong(), it.available.toLong(), it.used.toLong()) },
    network = network.map { NetworkDTO(it.macAddress, it.ipAddress) },
    manufacturer = manufacturer?.let { ManufacturerDTO(it.name, it.model, it.serialNumber, it.chassisType) }
)

fun InventoryDTO.toEntity(): Inventory = Inventory(
    hostname = hostname,
    serialNumber = serialNumber, 
    location = location,
    asignedTo = asignedTo,
    os = os?.let { Os(it.family, it.version, it.arch) },
    processor = processor?.let { Processor(it.name, it.identifier, it.vendor, it.frequency.toDouble(), it.cores) },
    memory = memory?.let { Memory(it.total, it.available, it.used) },
    disk = disk?.let { Disk(it.total.toInt(), it.available.toInt(), it.used.toInt()) },
    network = network.map { Network(it.macAddress, it.ipAddress) },
    manufacturer = manufacturer?.let { Manufacturer(it.name, it.model, it.serialNumber, it.chassisType) }
)