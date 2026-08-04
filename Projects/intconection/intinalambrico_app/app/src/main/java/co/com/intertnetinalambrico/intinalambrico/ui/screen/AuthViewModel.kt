package co.com.intertnetinalambrico.intinalambrico.ui.screen

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import net.openid.appauth.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import org.json.JSONObject

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val authService = AuthorizationService(application)

    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    companion object {
        private var idToken: String? = null
    }


    // Configuración de Keycloak con rutas de protocolo completas
    private val serviceConfig = AuthorizationServiceConfiguration(
        Uri.parse("https://auth.cloud.internetinalambrico.com.co/realms/app_intinalambrico/protocol/openid-connect/auth"),
        Uri.parse("https://auth.cloud.internetinalambrico.com.co/realms/app_intinalambrico/protocol/openid-connect/token"),
        null,
        Uri.parse("https://auth.cloud.internetinalambrico.com.co/realms/app_intinalambrico/protocol/openid-connect/logout")
    )

    fun login(launcher: ManagedActivityResultLauncher<Intent, ActivityResult>) {
        errorMessage = null
        val authRequest = AuthorizationRequest.Builder(
            serviceConfig,
            "intinalambrico_app",
            ResponseTypeValues.CODE,
            Uri.parse("co.com.intertnetinalambrico.intinalambrico:/oauth2redirect")
        ).setScope("openid profile email").build()

        try {
            val intent = authService.getAuthorizationRequestIntent(authRequest)
            launcher.launch(intent)
        } catch (e: Exception) {
            Log.e("AuthViewModel", "Error al lanzar intent: ${e.message}")
            errorMessage = "No se pudo abrir el navegador de autenticación"
        }
    }

    fun logout(launcher: ManagedActivityResultLauncher<Intent, ActivityResult>) {
        val endSessionRequest = EndSessionRequest.Builder(serviceConfig)
            .setPostLogoutRedirectUri(Uri.parse("co.com.intertnetinalambrico.intinalambrico:/oauth2redirect"))
            // Opcional: puedes pasar el idTokenHint si lo tienes guardado

        // CAMBIO 2: Asegurar que el idToken se adjunte a la petición
        if (idToken != null) {
            endSessionRequest.setIdTokenHint(idToken)
            Log.d("AuthViewModel", "Logout con idTokenHint: $idToken")
        } else {
            Log.w("AuthViewModel", "Logout sin idTokenHint (podría fallar en Keycloak)")
        }

        val intent = authService.getEndSessionRequestIntent(endSessionRequest.build())
        launcher.launch(intent)

        idToken = null // Limpiar el idToken después del logout
    }

    /**
     * Maneja el resultado del login.
     * Se eliminaron los "!!" para evitar que la app se cierre si el resultado es nulo.
     */
    fun handleAuthResult(result: ActivityResult, onRoleFound: (String) -> Unit) {
        val data = result.data
        if (data == null) {
            errorMessage = "Inicio de sesión cancelado"
            return
        }

        val response = AuthorizationResponse.fromIntent(data)
        val error = AuthorizationException.fromIntent(data)

        if (response != null) {
            isLoading = true
            val tokenRequest = response.createTokenExchangeRequest()

            authService.performTokenRequest(tokenRequest) { tokenResponse, tokenException ->
                if (tokenResponse != null) {
                    idToken = tokenResponse.idToken
                    Log.d("AuthViewModel", "idToken recibido: ${idToken}")

                    val accessToken = tokenResponse.accessToken
                    if (accessToken != null) {
                        val role = extractRoleFromJwt(accessToken)
                        onRoleFound(role)
                    } else {
                        errorMessage = "Error: No se recibió el token de acceso"
                    }
                } else {
                    Log.e("AuthViewModel", "Token error: ${tokenException?.message}")
                    errorMessage = "Error de validación: ${tokenException?.errorDescription ?: "Fallo de conexión"}"
                }
                isLoading = false
            }
        } else if (error != null) {
            Log.e("AuthViewModel", "Auth error: ${error.message}")
            errorMessage = "Error de autenticación: ${error.errorDescription ?: "Intente de nuevo"}"
        }
    }
    private fun extractRoleFromJwt(token: String): String {
        return try {
            val parts = token.split(".")
            if (parts.size < 2) return "user"

            val payload = String(Base64.decode(parts[1], Base64.URL_SAFE))
            val json = JSONObject(payload)

            // 1. Buscar en realm_access (Roles globales de Keycloak)
            val realmAccess = json.optJSONObject("realm_access")
            val realmRoles = realmAccess?.optJSONArray("roles")

            if (realmRoles != null) {
                for (i in 0 until realmRoles.length()) {
                    if (realmRoles.getString(i).equals("tecnico", ignoreCase = true)) {
                        return "tecnico"
                    }
                }
            }

            // 2. Buscar en resource_access (Roles específicos del cliente)
            val resourceAccess = json.optJSONObject("resource_access")
            val clientAccess = resourceAccess?.optJSONObject("intinalambrico_app")
            val clientRoles = clientAccess?.optJSONArray("roles")

            if (clientRoles != null) {
                for (i in 0 until clientRoles.length()) {
                    if (clientRoles.getString(i).equals("tecnico", ignoreCase = true)) {
                        return "tecnico"
                    }
                }
            }

            "user"
        } catch (e: Exception) {
            Log.e("AuthViewModel", "Error decodificando JWT: ${e.message}")
            "user"
        }
    }
    /*private fun extractRoleFromJwt(token: String): String {
        return try {
            val parts = token.split(".")
            if (parts.size < 2) return "user"

            val payload = String(Base64.decode(parts[1], Base64.URL_SAFE))
            val json = JSONObject(payload)

            val realmAccess = json.optJSONObject("realm_access")
            val rolesArray = realmAccess?.optJSONArray("roles")

            var finalRole = "user"

            if (rolesArray != null) {
                for (i in 0 until rolesArray.length()) {
                    val role = rolesArray.getString(i)
                    if (role.equals("tecnico", ignoreCase = true)) {
                        finalRole = "tecnico"
                        break
                    }
                }
            }
            finalRole
        } catch (e: Exception) {
            Log.e("AuthViewModel", "Error decodificando JWT: ${e.message}")
            "user"
        }
    }*/

    override fun onCleared() {
        super.onCleared()
        authService.dispose()
    }
}
/*class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val authService = AuthorizationService(application)

    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    // Configuración de tu Keycloak
    private val serviceConfig = AuthorizationServiceConfiguration(
        Uri.parse("https://auth.cloud.internetinalambrico.com.co/realms/app_intinalambrico/protocol/openid-connect/auth"),
        Uri.parse("https://auth.cloud.internetinalambrico.com.co/realms/app_intinalambrico/openid-connect/token")
    )

    fun login(launcher: ManagedActivityResultLauncher<Intent, ActivityResult>) {
        val authRequest = AuthorizationRequest.Builder(
            serviceConfig,
            "intinalambrico_app",
            ResponseTypeValues.CODE,
            Uri.parse("co.com.intertnetinalambrico.intinalambrico:/oauth2redirect")
        ).setScope("openid profile email").build()

        val intent = authService.getAuthorizationRequestIntent(authRequest)
        launcher.launch(intent)
    }

    // Función para manejar el resultado y extraer el ROL
    fun handleAuthResult(result: ActivityResult, onRoleFound: (String) -> Unit) {
        val response = AuthorizationResponse.fromIntent(result.data!!)
        val error = AuthorizationException.fromIntent(result.data)

        if (response != null) {
            isLoading = true
            // Intercambiar el Código por el Token
            authService.performTokenRequest(response.createTokenExchangeRequest()) { tokenResponse, tokenException ->
                if (tokenResponse != null) {
                    val accessToken = tokenResponse.accessToken
                    if (accessToken != null) {
                        val role = extractRoleFromJwt(accessToken)
                        onRoleFound(role)
                    }
                } else {
                    errorMessage = "Error al obtener token: ${tokenException?.message}"
                }
                isLoading = false
            }
        } else {
            errorMessage = "Login cancelado o fallido: ${error?.message}"
        }
    }

    /**
     * Decodifica el JWT y busca los roles en realm_access.roles
     */
    private fun extractRoleFromJwt(token: String): String {
        return try {
            val parts = token.split(".")
            if (parts.size < 2) return "user"

            // El Payload es la segunda parte del JWT
            val payload = String(Base64.decode(parts[1],
                Base64.URL_SAFE))
            val json = JSONObject(payload)

            // Keycloak guarda los roles en realm_access -> roles (Array)
            val realmAccess = json.optJSONObject("realm_access")
            val rolesArray = realmAccess?.optJSONArray("roles")

            var finalRole = "user" // Rol por defecto

            if (rolesArray != null) {
                for (i in 0 until rolesArray.length()) {
                    val role = rolesArray.getString(i)
                    // Priorizamos el rol de técnico si existe en el array
                    if (role.lowercase() == "tecnico") {
                        finalRole = "tecnico"
                        break
                    }
                }
            }
            finalRole
        } catch (e: Exception) {
            "user"
        }
    }

    override fun onCleared() {
        super.onCleared()
        authService.dispose()
    }
}*/
