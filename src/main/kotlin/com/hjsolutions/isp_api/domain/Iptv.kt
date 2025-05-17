package com.hjsolutions.isp_api.domain

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document; 
import java.time.LocalDateTime;

@Document(collection = "iptv")
data class Iptv(
    @Id
    val id: ObjectId? = null,

    val name: String? = null,            // Descripción opcional
    val url: String? = null, 
    val urlimage:String? = null,           // URL del stream
    val createdAt: LocalDateTime = LocalDateTime.now(),  // Fecha de creación
    val updatedAt: LocalDateTime? = null
) {
    // Additional methods or annotations can be added here if needed
}