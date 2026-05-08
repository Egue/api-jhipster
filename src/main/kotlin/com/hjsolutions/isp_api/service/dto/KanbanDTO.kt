package com.hjsolutions.isp_api.service.dto

import com.hjsolutions.isp_api.domain.Iptv
import com.hjsolutions.isp_api.service.dto.IptvDTO
import org.apache.xalan.serialize.Serializer
import java.io.Serializable

data class KanbanRequest(
    val type: String,
    val title: String,
    val sub: String,
    val task: List<TaskKanbanRequest> = emptyList()
)

data class TaskKanbanRequest(
    val title: String  ="",
    val description: String ="",
    val status: String ="",
    val sub : String =""
) : Serializable
{
    // Additional methods or annotations can be added here if needed
    constructor( task: TaskKanbanRequest): this(title = task.title , description = task.description , status = task.status , sub = task.sub)

    override fun toString(): String {
        return ""
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}


data class TaskKanbanUpdateRequest(
    val title: String?,
    val description: String?,
    val status: String?
)
