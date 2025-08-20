package com.hjsolutions.msm_send.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjsolutions.msm_send.ui.data.LoginRequest
import com.hjsolutions.msm_send.ui.data.LoginResponse
import com.hjsolutions.msm_send.ui.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class LoginState{
    object Idle : LoginState()
    object Loading: LoginState()
    data class Success(val data: LoginResponse) : LoginState()
    data class Error(val message:String) : LoginState()
}

class LoginViewModel(): ViewModel(){

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState
    private val repository: AuthRepository = AuthRepository()

    fun login(username:String , password : String){
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            val result = repository.login(username , password)
            _loginState.value = result.fold(
                onSuccess = {LoginState.Success(it)},
                onFailure = {LoginState.Error(it.message ?: "Unknown error")}
            )
        }
    }
}
