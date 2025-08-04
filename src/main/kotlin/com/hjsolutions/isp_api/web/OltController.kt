package com.hjsolutions.isp_api.web
import com.hjsolutions.isp_api.service.dto.ConfigurationOLT
import com.hjsolutions.isp_api.service.OltService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/kt")
class OltController(
    private val oltService:OltService
) {

    // This class is currently empty, but you can add methods to handle OLT-related requests.
    // For example, you might want to add endpoints to retrieve OLT configurations or manage OLT devices.
    
    // Example method (uncomment and implement as needed):
    // @GetMapping("/olt/configurations")
    // fun getOltConfigurations(): ResponseEntity<List<ConfigurationOLT>> {
    //     // Implement logic to retrieve OLT configurations
    // }

    //listar configuracion
    @GetMapping("/olt/configurations")
    fun getOltConfigurations(): ResponseEntity<List<ConfigurationOLT>> {
        // Implement logic to retrieve OLT configurations
        var listConfig: List<ConfigurationOLT> = oltService.getConfiguration() // Placeholder for actual configuration retrieval logic
        return ResponseEntity.ok(listConfig) // Placeholder return
    }


    
}