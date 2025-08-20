package com.hjsolutions.isp_api.repository

import com.hjsolutions.isp_api.domain.Sms
import org.springframework.data.mongodb.repository.MongoRepository

interface SmsRepository : MongoRepository<Sms , String> {

    fun findAllByStatus(status: Int): MutableList<Sms>

    fun findAllByStatusAndUserId(status:Int , userId:Int): MutableList<Sms>
}
