package com.hjsolutions.isp_api.service
import org.springframework.stereotype.Service 
import com.hjsolutions.isp_api.service.dto.ConfigurationOLT
import com.hjsolutions.isp_api.service.dto.EndPointOLT
import com.comunicamosmas.api.service.ISystemConfigService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.core.type.TypeReference
import com.comunicamosmas.api.domain.SystemConfig

@Service
class OltService(
    
    private val systemService: ISystemConfigService

) {
    // This class is currently empty, but you can add methods to handle OLT-related requests.
    // For example, you might want to add endpoints to retrieve OLT configurations or manage OLT devices.

    fun saveConfiguration(configOlt: List<ConfigurationOLT>): Boolean {
        // Implement logic to save OLT configuration
        // This is a placeholder implementation
        val mapper = ObjectMapper()
        val configOltString = mapper.writeValueAsString(configOlt)

        val system = SystemConfig()
        system.origen = "configuration_olt"
        system.comando = configOltString
        systemService.save(system)
        return true
    }

    fun getConfiguration(): List<ConfigurationOLT> {
        // Implement logic to retrieve OLT configuration
        val system = systemService.findByOrigen("configuration_olt")
        if (system == null || system.comando.isNullOrEmpty()) {
            return emptyList()
        }
        val mapper = ObjectMapper()
        return mapper.readValue(system.comando, object : TypeReference<List<ConfigurationOLT>>() {})
    }
}