package com.hjsolutions.isp_api.web.rest

import java.time.LocalDateTime

data class MoveTaskRequest(
    val taskId: String = "",
    val sourceKanbanId: String = "",
    val targetKanbanId: String =""
)

data class MoveTaskResponse(
    val taskId: String,
    val fromKanban: String,
    val toKanban: String,
    val movedAt: LocalDateTime = LocalDateTime.now()
)
