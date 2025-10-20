package com.hjsolutions.isp_api.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable
import java.time.Instant
import java.time.LocalDateTime
import java.util.UUID

@Document("sms")
data class Sms(
    @Id
    var id: String = UUID.randomUUID().toString(),
    var userId : Number? = 0,
    var idServicio: Number? = 0,
    var name:String? = "",
    var description:String?= "",
    var numbers:List<Numbers>?= emptyList<Numbers>(),
    var status: Int? = 1,
    var cretedAt : LocalDateTime = LocalDateTime.now()
): Serializable{}

data class Numbers(
    var number:String ?  = ""
): Serializable{}
