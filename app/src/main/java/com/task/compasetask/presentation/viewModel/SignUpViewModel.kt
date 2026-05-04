package com.task.compasetask.presentation.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SignUpUiState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSignUpSuccess: Boolean = false
)

sealed class SignUpEvent {
    data class UsernameChanged(val username: String) : SignUpEvent()
    data class EmailChanged(val email: String) : SignUpEvent()
    data class PasswordChanged(val password: String) : SignUpEvent()
    data class ConfirmPasswordChanged(val confirm: String) : SignUpEvent()
    object SignUpClicked : SignUpEvent()
    object ResetError : SignUpEvent()
}

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.UsernameChanged -> _uiState.value = _uiState.value.copy(username = event.username)
            is SignUpEvent.EmailChanged -> _uiState.value = _uiState.value.copy(email = event.email)
            is SignUpEvent.PasswordChanged -> _uiState.value = _uiState.value.copy(password = event.password)
            is SignUpEvent.ConfirmPasswordChanged -> _uiState.value = _uiState.value.copy(confirmPassword = event.confirm)
            SignUpEvent.SignUpClicked -> performSignUp()
            SignUpEvent.ResetError -> _uiState.value = _uiState.value.copy(errorMessage = null)
        }
    }

    private fun performSignUp() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            val state = _uiState.value
            if (state.username.isBlank() || state.email.isBlank() || state.password.isBlank()) {
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = "All fields required")
                return@launch
            }
            if (state.password != state.confirmPassword) {
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = "Passwords do not match")
                return@launch
            }
            if (state.password.length < 4) {
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = "Password too short")
                return@launch
            }
            // محاكاة إنشاء حساب ناجح
            _uiState.value = _uiState.value.copy(isLoading = false, isSignUpSuccess = true)
        }
    }
}