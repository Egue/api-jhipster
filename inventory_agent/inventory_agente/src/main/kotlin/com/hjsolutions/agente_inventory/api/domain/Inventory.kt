package com.hjsolutions.agente_inventory.api.domain

 
data class Inventory(
    var hostname: String, 
    var os: Os?,
    var processor: Processor?,
    var memory: Memory?,
    var disk: Disk?,
    var network: List<Network>,
    var manufacturer:Manufacturer? 
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