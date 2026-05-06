package com.task.compasetask.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SignInUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSignInSuccess: Boolean = false
)

sealed class SignInEvent {
    data class EmailChanged(val email: String) : SignInEvent()
    data class PasswordChanged(val password: String) : SignInEvent()
    object SignInClicked : SignInEvent()
    object ForgotPasswordClicked : SignInEvent()
    object ResetError : SignInEvent()
}

@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.EmailChanged -> {
                _uiState.value = _uiState.value.copy(email = event.email, errorMessage = null)
            }
            is SignInEvent.PasswordChanged -> {
                _uiState.value = _uiState.value.copy(password = event.password, errorMessage = null)
            }
            SignInEvent.SignInClicked -> performSignIn()
            SignInEvent.ForgotPasswordClicked -> {
                _uiState.value = _uiState.value.copy(errorMessage = "Reset link sent to your email")
            }
            SignInEvent.ResetError -> {
                _uiState.value = _uiState.value.copy(errorMessage = null)
            }
        }
    }

    private fun performSignIn() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            val email = _uiState.value.email
            val password = _uiState.value.password
            if (email.isNotBlank() && password.length >= 4) {
                _uiState.value = _uiState.value.copy(isLoading = false, isSignInSuccess = true)
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Invalid email or password (min 4 chars)"
                )
            }
        }
    }
}