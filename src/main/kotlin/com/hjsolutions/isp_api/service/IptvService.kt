package com.hjsolutions.isp_api.service

import org.springframework.stereotype.Service
import com.hjsolutions.isp_api.domain.Iptv
import com.hjsolutions.isp_api.repository.IptvRepository
import com.hjsolutions.isp_api.service.dto.IptvDTO 


@Service 
class IptvService(private val iptvRepository: IptvRepository) {
    // This service class can be used to implement business logic related to IPTV
    // For example, you can add methods to interact with the repository, perform validations, etc.
    // Currently, it's empty and can be filled in as needed.

    fun save(iptv: IptvDTO): Iptv {
        // Convert DTO to domain entity
        val iptvEntity = Iptv( 
            name = iptv.name,
            urlimage = iptv.urlimage,
            url = iptv.url
        )
        return iptvRepository.save(iptvEntity)    
    }

    fun findAll(): List<Iptv> {
        return iptvRepository.findAll()
    }

}