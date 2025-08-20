package com.hjsolutions.msm_send.ui.repository

import com.hjsolutions.msm_send.ui.data.LoginRequest
import com.hjsolutions.msm_send.ui.data.LoginResponse
import com.hjsolutions.msm_send.ui.services.ApiClient
import com.hjsolutions.msm_send.ui.services.AuthService

class AuthRepository() {

    var apiClient :ApiClient = ApiClient

    suspend fun login(username:String , password:String):Result<LoginResponse>{
        return try {
            val response = apiClient.api.login(LoginRequest(username , password))
            if(response.isSuccessful){
                Result.success(response.body()!!)
            }else{
                Result.failure(Exception("Login failled: ${response.code()}"))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
    }
}
