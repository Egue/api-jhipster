package com.hjsolutions.msm_send.ui.data.remote.api

import com.hjsolutions.msm_send.ui.data.local.entities.SmsCampaign
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SmsApiService {

    @GET("kt/sms/sms/query")
    suspend fun getCampaigns(@Header("Authorization") token:String , @Query("userId") userId:String, @Query("status") status:String):List<SmsCampaign>
}
