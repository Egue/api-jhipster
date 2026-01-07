package com.hjsolutions.isp_api.repository

import com.hjsolutions.isp_api.domain.Templates
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TemplateRepository : MongoRepository<Templates , String> {


    fun findOneByIdServicioAndName(idServicio:Number , name:String): Templates?

}
