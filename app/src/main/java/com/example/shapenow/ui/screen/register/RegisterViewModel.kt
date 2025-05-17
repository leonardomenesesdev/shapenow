package com.example.shapenow.ui.screen.register

import androidx.lifecycle.ViewModel
import com.example.shapenow.data.datasource.model.User
import com.example.shapenow.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RegisterViewModel : ViewModel() {
    private val authRepository = AuthRepository()
    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState
    fun register(email: String, senha: String, name: String) {
        _registerState.value = RegisterState.Loading

        authRepository.register(email, senha, name) { success, error ->
            _registerState.value = if(success){
                RegisterState.Success
            } else{
                RegisterState.Error(error ?: "Erro no cadastro")
            }
        }
    }


    sealed class RegisterState {
        object Idle : RegisterState()
        object Loading : RegisterState()
        object Success : RegisterState()
        data class Error(val message: String) : RegisterState()
    }

}
