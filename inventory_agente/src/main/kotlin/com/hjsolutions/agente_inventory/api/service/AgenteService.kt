
package com.hjsolutions.agente_inventory.api.service
import org.springframework.stereotype.Service
import oshi.SystemInfo
import com.hjsolutions.agente_inventory.api.domain.Inventory
import com.hjsolutions.agente_inventory.api.domain.Processor
import com.hjsolutions.agente_inventory.api.domain.Memory
import com.hjsolutions.agente_inventory.api.domain.Disk
import com.hjsolutions.agente_inventory.api.domain.Network
import com.hjsolutions.agente_inventory.api.domain.Os
import com.hjsolutions.agente_inventory.api.domain.Manufacturer
import java.net.InetAddress
import oshi.hardware.ComputerSystem

@Service
class AgenteService {

    // Add your service methods here
    fun generateQuery(): Inventory {
        // Placeholder for actual implementation
        

        var si : SystemInfo = SystemInfo()
        var os = si.operatingSystem
        var cpu = si.hardware.processor
        var disk = si.hardware.diskStores.firstOrNull() ?: throw IllegalStateException("No disk found")
        var memory = si.hardware.memory
        var computerSystem = si.hardware.computerSystem

        var invetory : Inventory = Inventory(
            hostname = InetAddress.getLocalHost().hostName,
            os = Os(
                family = os.family,
                version = os.getVersionInfo().version,
                arch = System.getProperty("os.arch")
            ),
            processor = Processor(
                name = cpu.processorIdentifier.name,
                identifier = cpu.processorIdentifier.identifier,
                vendor = cpu.processorIdentifier.vendor,
                frequency = cpu.maxFreq.toDouble(),
                cores = cpu.logicalProcessorCount
            ),
            memory = Memory(
                total = memory.total,
                available = memory.available,
                used = memory.total - memory.available
            ),
            disk = Disk(
                total = si.hardware.diskStores.sumOf { it.size }.toInt(),
                available = (disk.getSize() / (1024 * 1024)).toInt(),
                used = ((disk.getSize() - disk.writeBytes) / disk.getSize() * 100).toInt()
            ),
            network = si.hardware.networkIFs.map {
                Network(
                    macAddress = it.macaddr,
                    ipAddress = it.getIPv4addr().firstOrNull() ?: "N/A"
                )
            },
            manufacturer = Manufacturer(
                name = computerSystem.manufacturer,
                model = computerSystem.model,
                serialNumber = computerSystem.serialNumber ?: "N/A",
                chassisType = getChassisType(computerSystem)
        )
        )

        return invetory;
    } 

    fun getChassisType(computerSystem : ComputerSystem): String {
        // Placeholder for actual implementation

        val model: String = computerSystem.model ?: "Unknown"
        val manufacturer: String = computerSystem.manufacturer ?: "Unknown"

        if (model.isEmpty() || manufacturer.isEmpty()) {
            return "Unknown Chassis Type"
        }else if (model.contains("Laptop", ignoreCase = true) || model.contains("Notebook", ignoreCase = true)) {
            return "Laptop"
        } else if (model.contains("Desktop", ignoreCase = true)) {
            return "Desktop"
        } else if (model.contains("Server", ignoreCase = true)) {
            return "Server"
        }
        return "Other"
    }
}