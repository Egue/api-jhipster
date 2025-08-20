package com.hjsolutions.msm_send.ui.services

import com.hjsolutions.msm_send.ui.data.LoginRequest
import com.hjsolutions.msm_send.ui.data.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response

interface AuthService{

    @POST("authenticate")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}
