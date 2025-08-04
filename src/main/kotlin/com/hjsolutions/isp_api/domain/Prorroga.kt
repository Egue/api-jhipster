package com.hjsolutions.isp_api.domain
import org.springframework.data.mongodb.core.mapping.Document
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import java.time.LocalDateTime
import java.util.UUID
 
@Document(collection = "prorrogas")
data class Prorroga(
    @Id
    var id: String = UUID.randomUUID().toString(),
    var idContrato : Long? = 0,
    var createBy : Long? = 0,
    var fechaProrroga : String?  =  null   ,
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var state : String? = "A"  ,
    var updatedAt: LocalDateTime? = null
) {
    // Additional methods or annotations can be added here if needed
}