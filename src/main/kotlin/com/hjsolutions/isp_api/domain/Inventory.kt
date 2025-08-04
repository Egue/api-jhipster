package com.hjsolutions.isp_api.domain
import org.springframework.data.mongodb.core.mapping.Document; 
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Document(collection = "inventory")
data class Inventory(
    @Id
    var id: ObjectId? = null,
    var hostname: String, 
    var serialNumber: String? = null,
    var location: String? = null,
    var asignedTo: String? = null,
    var os: Os?,
    var processor: Processor?,
    var memory: Memory?,
    var disk: Disk?,
    var network: List<Network>,
    var manufacturer: Manufacturer? = null,
) {
    // Additional methods or validations can be added here if needed
}

data class Manufacturer(
    val name: String,
    val model: String,
    val serialNumber: String,
    val chassisType: String,
) {
    // Additional methods or validations can be added here if needed
}

data class Disk(
    val total: Int,
    val available: Int,
    val used: Int
) {
    // Additional methods or validations can be added here if needed
}
data class Network(
    val macAddress: String,
    val ipAddress: String
) {
    // Additional methods or validations can be added here if needed
}

data class Memory(
    val total: Long,
    val available: Long,
    val used: Long
) {
    // Additional methods or validations can be added here if needed
}

data class Os(
    val family: String,
    val version: String,
    val arch: String
){

}

data class Processor(
    val name: String,
    val identifier: String,
    val vendor: String,
    val frequency: Double,
    val cores: Int
) {
    // Additional methods or validations can be added here if needed
}