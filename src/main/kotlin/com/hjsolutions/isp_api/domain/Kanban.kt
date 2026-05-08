package com.hjsolutions.isp_api.domain

import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable
import java.time.Instant
import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.Id

@Document(collection = "kanban")
data class Kanban(
    @Id
    var id : String = UUID.randomUUID().toString(),
    var type:String,
    var title:String,
    var sub: String,
    var task : List<TaskKanban>
): Serializable{}

data class TaskKanban(
    var id : String = UUID.randomUUID().toString(),
    var title: String,
    var description: String,
    var status: String,
    var createdAt: LocalDateTime = LocalDateTime.now(),
): Serializable{}
