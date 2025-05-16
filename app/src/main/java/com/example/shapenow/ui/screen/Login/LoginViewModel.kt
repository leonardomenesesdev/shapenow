package com.example.shapenow.viewmodel

import androidx.lifecycle.ViewModel
import com.example.shapenow.data.datasource.model.User
import com.example.shapenow.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel : ViewModel() {

    private val authRepository = AuthRepository()

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(email: String, senha: String) {
        _loginState.value = LoginState.Loading

        authRepository.login(email, senha) { success, user, error ->
            _loginState.value = if (success) {
                LoginState.Success(user)
            } else {
                LoginState.Error(error ?: "Erro desconhecido")
            }
        }
    }


    sealed class LoginState {
        object Idle : LoginState()
        object Loading : LoginState()
        data class Success(val user: User?) : LoginState() // <- agora recebe o User
        data class Error(val message: String) : LoginState()
    }
}
