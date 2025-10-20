package com.hjsolutions.contratos_isp.data.repository

import android.util.Log
import com.hjsolutions.contratos_isp.api.client.AppWriteClient
import io.appwrite.exceptions.AppwriteException
import io.appwrite.models.Session
import io.appwrite.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

sealed class AuthException(message: String) : Exception(message)
class InvalidCredentialsException(message: String) : AuthException(message)
class TooManyRequestException(message: String) : AuthException(message)
class LoginException(message: String) : AuthException(message)

class LoginRepository {

    private val client = AppWriteClient.client
    private val account = AppWriteClient.account

    suspend fun login(email: String, password: String): Result<Session> {
        return withContext(Dispatchers.IO) {
            try {
                Log.e("GetCurrentLogin" , "entro aca")
                val sesion = account.createEmailPasswordSession(
                    email = email,
                    password = password
                )
                Log.e("GetCurrentLogin" , "${sesion}")
                Result.success(sesion)
            } catch (e: AppwriteException) {
                when (e.code) {
                    401 -> Result.failure(InvalidCredentialsException("Error de credenciales"))
                    429 -> Result.failure(TooManyRequestException("Demasiados intentos"))
                    else -> Result.failure(LoginException("Error de conexion"))
                }
            } catch (e: Exception) {
                Result.failure(LoginException("Error inexperado: ${e.message}"))
            }
        }
    }

    suspend fun getCurrentUser(): Result<User<Map<String, Any>>> {
        return withContext(Dispatchers.IO) {
            Log.e("GetCurrentUser" , "entro aca")
            try {
                val user = account.get()
                Log.e("GetCurrentUser" , "${user}")
                Result.success(user)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun logout(): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                account.deleteSession("current")
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun fetchPingLlog() {

        withContext(Dispatchers.IO) {
            try {
                val response = client.ping()

            } catch (e: AppwriteException) {
                print(e.message)
            }
        }

    }
}
