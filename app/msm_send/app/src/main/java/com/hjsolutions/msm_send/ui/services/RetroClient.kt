package com.hjsolutions.msm_send.ui.services

import com.hjsolutions.msm_send.ui.data.remote.api.SmsApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient{

    private const val BASE_URL = "http://131.221.41.20:8061/api/"

    val api: AuthService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(AuthService::class.java)

    val sms: SmsApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(SmsApiService::class.java)
}
