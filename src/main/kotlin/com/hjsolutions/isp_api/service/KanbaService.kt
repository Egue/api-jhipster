package com.hjsolutions.isp_api.service

import com.hjsolutions.isp_api.domain.Kanban
import com.hjsolutions.isp_api.domain.TaskKanban
import com.hjsolutions.isp_api.repository.KanbanRepository
import com.hjsolutions.isp_api.service.dto.KanbanRequest
import com.hjsolutions.isp_api.service.dto.TaskKanbanRequest
import com.hjsolutions.isp_api.service.dto.TaskKanbanUpdateRequest
import com.hjsolutions.isp_api.web.rest.DuplicateTaskException
import com.hjsolutions.isp_api.web.rest.KanbanNotFoundException
import com.hjsolutions.isp_api.web.rest.MoveTaskRequest
import com.hjsolutions.isp_api.web.rest.MoveTaskResponse
import com.hjsolutions.isp_api.web.rest.TaskNotFoundException
import liquibase.pro.packaged.ka
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class KanbaService(
    private val kanbanRepository : KanbanRepository
)
{
    companion object {
        private val DEFAULT_TYPES = listOf("todo", "inprogress", "done")
    }

    fun findOrCreateBySub(sub: String): List<Kanban> {
        val existing = kanbanRepository.findBySub(sub)

        if (existing.isNotEmpty()) return existing


        val defaults = DEFAULT_TYPES.map { type ->
            Kanban(
                type = type,
                title = type.replaceFirstChar { it.uppercase() },
                sub = sub,
                task = emptyList()
            )
        }
        return kanbanRepository.saveAll(defaults)
    }

    fun findById(id: String): Kanban =
        kanbanRepository.findById(id)
            .orElseThrow { NoSuchElementException("Kanban not found with id: $id") }

    fun create(request: KanbanRequest): Kanban {
        val kanban = Kanban(
            type = request.type,
            title = request.title,
            sub = request.sub,
            task = request.task.map { it.toEntity() }
        )
        return kanbanRepository.save(kanban)
    }

    fun update(id: String, request: KanbanRequest): Kanban {
        val existing = findById(id)
        val updated = existing.copy(
            type = request.type,
            title = request.title,
            sub = request.sub,
            task = request.task.map { it.toEntity() }
        )
        return kanbanRepository.save(updated)
    }

    fun delete(id: String) {
        if (!kanbanRepository.existsById(id)) {
            throw NoSuchElementException("Kanban not found with id: $id")
        }
        kanbanRepository.deleteById(id)
    }


    fun addTask(request: TaskKanbanRequest ,  kanbanId : String): Kanban {
        val kanban = kanbanRepository.findById(kanbanId).orElseThrow() //kanbanRepository.findBySubAndType(sub = request.sub, type = request.)

        val updated = kanban.copy(task = kanban.task + request.toEntity())
        return kanbanRepository.save(updated)
    }

    //move task
    fun moveTask(request: MoveTaskRequest): MoveTaskResponse {
        // 1. Cargar ambos kanbans
        val sourceKanban = kanbanRepository.findById(request.sourceKanbanId)
            .orElseThrow { KanbanNotFoundException(request.sourceKanbanId) }

        val targetKanban = kanbanRepository.findById(request.targetKanbanId)
            .orElseThrow { KanbanNotFoundException(request.targetKanbanId) }

        // 2. Encontrar el task en el origen
        val task = sourceKanban.task.find { it.id == request.taskId }
            ?: throw TaskNotFoundException(request.taskId, request.sourceKanbanId)

        // 3. Validar que no exista ya en el destino
        if (targetKanban.task.any { it.id == request.taskId }) {
            throw DuplicateTaskException(request.taskId, request.targetKanbanId)
        }

        // 4. Remover del origen y agregar al destino
        val updatedSource = sourceKanban.copy(
            task = sourceKanban.task.filter { it.id != request.taskId }
        )
        val updatedTarget = targetKanban.copy(
            task = targetKanban.task + task
        )

        // 5. Persistir ambos documentos
        kanbanRepository.save(updatedSource)
        kanbanRepository.save(updatedTarget)

        return MoveTaskResponse(
            taskId = task.id,
            fromKanban = sourceKanban.id,
            toKanban = targetKanban.id
        )
    }

    fun deleteTask(kanbanId: String, taskId: String): Kanban {
        val kanban = findById(kanbanId)
        val updatedTasks = kanban.task.filter { it.id != taskId }
        if (updatedTasks.size == kanban.task.size) {
            throw NoSuchElementException("Task not found with id: $taskId")
        }
        return kanbanRepository.save(kanban.copy(task = updatedTasks))
    }

    // --- Mapper ---
    private fun TaskKanbanRequest.toEntity() = TaskKanban(
        title = this.title,
        description = this.description,
        status = this.status,
        createdAt = LocalDateTime.now()
    )
}
