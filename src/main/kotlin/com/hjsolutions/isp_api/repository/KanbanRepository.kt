package com.hjsolutions.isp_api.repository

import com.hjsolutions.isp_api.domain.Kanban
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface KanbanRepository : MongoRepository<Kanban , String> {


    fun findBySub(sub: String): List<Kanban>

    fun findBySubAndType(sub: String, type: String): Kanban?

    @Query("{ 'sub': ?0, 'task.status': ?1 }")
    fun findBySubAndTaskStatus(sub: String, status: String): List<Kanban>
}
