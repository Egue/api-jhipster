package com.hjsolutions.contratos_isp.ui.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjsolutions.contratos_isp.data.repository.LoginRepository
import io.appwrite.models.User
import kotlinx.coroutines.launch

data class AuthUiState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val errorMessage: String? = null,
    val user: User<Map<String, Any>>? = null
)

class LoginViewModel() : ViewModel() {

    private val loginRepository = LoginRepository()
    var uiState by mutableStateOf(AuthUiState())

    init {
        checkout()
    }

    private fun checkout() {

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            loginRepository.getCurrentUser()
                .onSuccess { user ->
                    uiState = uiState.copy(
                        isLoading = false,
                        isLoggedIn = true,
                        user = user
                    )
                }
                .onFailure { error->
                    uiState = uiState.copy(
                        isLoading = false,
                        isLoggedIn = false,
                        errorMessage = error.message
                        )
                }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)
            Log.d("Login :" , "${email}  : ${password}")
            loginRepository.login(email = email, password = password)
                .onSuccess {

                    checkout()
                }.onFailure { error ->
                    uiState = uiState.copy(isLoading = false, errorMessage = error.message)
                }
        }
    }

    fun logout() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            loginRepository.logout()
                .onSuccess {
                    uiState = uiState.copy(
                        isLoading = false,
                        isLoggedIn = false,
                        user = null
                    )
                }.onFailure { error ->
                    uiState = uiState.copy(
                        isLoading = false,
                        errorMessage = error.message
                    )

                }
        }
    }

    fun ping() {
        viewModelScope.launch {
            loginRepository.fetchPingLlog()
        }
    }


}
