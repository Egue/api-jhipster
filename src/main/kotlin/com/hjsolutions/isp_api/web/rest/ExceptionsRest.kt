package com.hjsolutions.isp_api.web.rest

class KanbanNotFoundException(id: String) :
    RuntimeException("Kanban no encontrado con id: $id")

class TaskNotFoundException(taskId: String, kanbanId: String) :
    RuntimeException("Task '$taskId' no encontrado en kanban '$kanbanId'")

class DuplicateTaskException(taskId: String, kanbanId: String) :
    RuntimeException("El task '$taskId' ya existe en el kanban destino '$kanbanId'")
