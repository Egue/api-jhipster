package com.hjsolutions.isp_api.service.dto
import java.io.Serializable
import com.hjsolutions.isp_api.domain.Inventory
import com.hjsolutions.isp_api.domain.Os
import com.hjsolutions.isp_api.domain.Processor
import com.hjsolutions.isp_api.domain.Memory
import com.hjsolutions.isp_api.domain.Disk
import com.hjsolutions.isp_api.domain.Network
import com.hjsolutions.isp_api.domain.Manufacturer 

data class InventoryDTO(
    var hostname: String = "",
    var serialNumber: String? = null,
    var location: String? = null,
    var asignedTo: String? = null,
    var os: OsDTO? = null,
    var processor: ProcessorDTO? = null,
    var memory: MemoryDTO? = null,
    var disk: DiskDTO? = null,
    var network: List<NetworkDTO> = emptyList(),
    var manufacturer: ManufacturerDTO? = null
) : Serializable

data class OsDTO(val family: String="", val version: String="", val arch: String="") : Serializable

data class ProcessorDTO(
    val name: String="",
    val identifier: String="",
    val vendor: String="",
    val frequency: Long=0,
    val cores: Int = 0
) : Serializable

data class MemoryDTO(val total: Long = 0, val available: Long = 0, val used: Long = 0) : Serializable

data class DiskDTO(val total: Long = 0, val available: Long = 0, val used: Long = 0) : Serializable

data class NetworkDTO(val macAddress: String="", val ipAddress: String="") : Serializable

data class ManufacturerDTO(
    val name: String="",
    val model: String="",
    val serialNumber: String="",
    val chassisType: String=""
) : Serializable