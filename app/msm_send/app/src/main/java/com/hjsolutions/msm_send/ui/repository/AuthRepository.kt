package com.hjsolutions.msm_send.ui.repository

import com.hjsolutions.msm_send.ui.data.LoginRequest
import com.hjsolutions.msm_send.ui.data.LoginResponse
import com.hjsolutions.msm_send.ui.data.local.dao.SessionDao
import com.hjsolutions.msm_send.ui.data.local.entities.UserSession
import com.hjsolutions.msm_send.ui.services.ApiClient
import com.hjsolutions.msm_send.ui.services.AuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository(
    private val sessionDao: SessionDao
) {

    var apiClient :ApiClient = ApiClient

    suspend fun login(username:String , password:String):Result<LoginResponse>{
        return try {
            val response = apiClient.api.login(LoginRequest(username , password))
        when{
            response.isSuccessful -> {
                val loginResponse = response.body()
                if(loginResponse == null)
                {
                    return Result.failure(Exception("Respuesta vacía del servidor"))
                }
                if (loginResponse.id_token.isNullOrEmpty()) {
                    return Result.failure(Exception("Token de acceso no válido"))
                }
                val usuario = loginResponse.usuario
                if (usuario == null) {
                    return Result.failure(Exception("Datos de usuario no encontrados"))
                }

                if (usuario.id == null) {
                    return Result.failure(Exception("ID de usuario no válido"))
                }

                if (usuario.login.isNullOrEmpty()) {
                    return Result.failure(Exception("Login de usuario no válido"))
                }

                withContext(Dispatchers.IO){
                    val session = UserSession(
                        token = loginResponse.id_token,
                        userId = usuario.id.toString(),
                        login = usuario.login,
                        rol = usuario.rol ?:""
                    )
                    sessionDao.saveSession(session)
                }
                Result.success(loginResponse)
            }
            response.code() == 401->{
                Result.failure(Exception("Credenciales invalidas"))
            }
            response.code() == 404 -> {
                Result.failure(Exception("Usuario no encontrado"))
            }
            else -> {
                Result.failure(Exception("Error de servidor: ${response.code()} - ${response.message()}"))
            }

        }
        }catch (e:Exception){
            Result.failure(e)
        }
    }
}
