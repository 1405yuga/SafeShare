package com.example.safeshare.ui.screens.authentication.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.safeshare.utils.input_validators.AuthInputValidators
import com.example.safeshare.utils.screen_state.ScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _screenState = MutableStateFlow<ScreenState<String>>(ScreenState.PreLoad())
    val screenState: StateFlow<ScreenState<String>> = _screenState

    var email by mutableStateOf("")
        private set

    var emailError by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var passwordError by mutableStateOf("")
        private set

    var showPassword by mutableStateOf(false)
        private set

    fun onEmailChange(newEmail: String) {
        email = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    fun togglePasswordVisibility() {
        showPassword = !showPassword
    }

    private fun inputValidators(): Boolean {
        emailError = AuthInputValidators.validateEmail(email = email) ?: ""
        passwordError = AuthInputValidators.validatePassword(password = password) ?: ""
        return emailError.isBlank() and passwordError.isBlank()
    }

    fun emailLogin() {
        if (!inputValidators()) return
        _screenState.value = ScreenState.Loading()
        viewModelScope.launch {
            _screenState.value = try {
                // TODO: perform authentication
                ScreenState.Loaded("Login successfully")
            } catch (e: Exception) {
                e.printStackTrace()
                ScreenState.Error("Unable to Login. Something went wrong!")
            }
        }
    }
}