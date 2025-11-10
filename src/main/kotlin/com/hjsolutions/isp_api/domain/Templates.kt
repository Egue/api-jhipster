package com.hjsolutions.isp_api.domain

import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID
import javax.persistence.Id

@Document(collection = "templates")
data class Templates (
    @Id
    var id: String = UUID.randomUUID().toString(),
    var name:String? = "",
    var template:String? = "",
    var idServicio:Number? = 0
){}
