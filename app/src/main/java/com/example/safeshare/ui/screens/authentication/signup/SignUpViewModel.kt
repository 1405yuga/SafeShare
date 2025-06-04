package com.example.safeshare.ui.screens.authentication.signup

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

class SignUpViewModel : ViewModel() {
    private val _screenState = MutableStateFlow<ScreenState<String>>(ScreenState.PreLoad())
    val screenState: StateFlow<ScreenState<String>> = _screenState

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var showPassword by mutableStateOf(false)
        private set

    var confirmPassword by mutableStateOf("")
        private set

    var showConfirmPassword by mutableStateOf(false)
        private set

    var emailError by mutableStateOf("")
        private set

    var passwordError by mutableStateOf("")
        private set

    var confirmPasswordError by mutableStateOf("")
        private set

    fun onEmailChange(newEmail: String) {
        email = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    fun onConfirmPasswordChange(newPassword: String) {
        confirmPassword = newPassword
    }

    fun togglePasswordVisibility() {
        showPassword = !showPassword
    }

    fun toggleConfirmPasswordVisibility() {
        showConfirmPassword = !showConfirmPassword
    }

    private fun inputValidators(): Boolean {
        emailError = AuthInputValidators.validateEmail(email = email) ?: ""
        passwordError = AuthInputValidators.validatePassword(password = password) ?: ""
        confirmPasswordError = AuthInputValidators.validateConfirmPassword(
            password = password,
            confirmPassword = confirmPassword
        ) ?: ""
        return emailError.isBlank() and passwordError.isBlank() and confirmPasswordError.isBlank()
    }

    fun emailSignUp(email: String, password: String) {
        if (!inputValidators()) return
        _screenState.value = ScreenState.Loading()
        viewModelScope.launch {
            _screenState.value = try {
                // TODO: signUp
                ScreenState.Loaded("Account created! Check your email and verify it.")
            } catch (e: Exception) {
                e.printStackTrace()
                ScreenState.Error("Unable to SignUp. Something went wrong!")
            }
        }
    }
}