package com.hjsolutions.isp_api.web

import com.hjsolutions.isp_api.domain.Kanban
import com.hjsolutions.isp_api.service.KanbaService
import com.hjsolutions.isp_api.service.dto.KanbanRequest
import com.hjsolutions.isp_api.service.dto.TaskKanbanRequest
import com.hjsolutions.isp_api.service.dto.TaskKanbanUpdateRequest
import com.hjsolutions.isp_api.web.rest.MoveTaskRequest
import com.hjsolutions.isp_api.web.rest.MoveTaskResponse
import io.jsonwebtoken.Jwt
import io.jsonwebtoken.Jwts
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/kt/kanban")
class KanbanController(
    private val kanbanService : KanbaService
) { //ver tutoriales de n8n https://www.youtube.com/watch?v=3IvcIPDGB1k , capacitaion https://youtu.be/M5ZSjBnACtM?si=Rf51c1pAjEnoo5Qh

    /**
     * Endpoint principal: busca los tableros del usuario por su `sub` del JWT.
     * Si no existe ninguno, crea automáticamente: todo, inprogress, done.
     */
    @GetMapping("/find/{user}")
    fun findOrCreate(@PathVariable user : String): ResponseEntity<List<Kanban>> {
           // extrae el campo 'sub' del token
        return ResponseEntity.ok(kanbanService.findOrCreateBySub(user))
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): ResponseEntity<Kanban> =
        ResponseEntity.ok(kanbanService.findById(id))

    @PostMapping
    fun create(@RequestBody request: KanbanRequest): ResponseEntity<Kanban> =
        ResponseEntity.status(HttpStatus.CREATED).body(kanbanService.create(request))

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: String,
        @RequestBody request: KanbanRequest
    ): ResponseEntity<Kanban> =
        ResponseEntity.ok(kanbanService.update(id, request))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Void> {
        kanbanService.delete(id)
        return ResponseEntity.noContent().build()
    }

    // --- Task endpoints ---

    @PostMapping("/{kanbanId}/tasks")
    fun addTask(
        @RequestBody request: TaskKanbanRequest,
        @PathVariable("kanbanId") kanbanId: String
    ): ResponseEntity<Kanban> =
        ResponseEntity.status(HttpStatus.CREATED)
            .body(kanbanService.addTask( request , kanbanId))

    @PostMapping("/move-task")
    fun moveTask(@RequestBody request: MoveTaskRequest): ResponseEntity<MoveTaskResponse> {
        val result = kanbanService.moveTask(request)
        return ResponseEntity.ok(result)
    }

    @DeleteMapping("/{kanbanId}/tasks/{taskId}")
    fun deleteTask(
        @PathVariable kanbanId: String,
        @PathVariable taskId: String
    ): ResponseEntity<Kanban> =
        ResponseEntity.ok(kanbanService.deleteTask(kanbanId, taskId))
}
