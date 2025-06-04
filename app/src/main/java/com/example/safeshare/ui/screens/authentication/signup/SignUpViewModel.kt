package com.example.safeshare.ui.screens.authentication.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.safeshare.network.AuthRepository
import com.example.safeshare.utils.input_validators.AuthInputValidators
import com.example.safeshare.utils.screen_state.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {
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

    fun emailSignUp() {
        if (!inputValidators()) return
        _screenState.value = ScreenState.Loading()
        viewModelScope.launch {
            _screenState.value = try {
                // TODO: signUp
                val result = authRepository.createAccountWithEmailAndPassword(
                    email = this@SignUpViewModel.email,
                    password = this@SignUpViewModel.password
                )
                if (result?.user != null) {
                    ScreenState.Loaded("Account created!")
                } else {
                    ScreenState.Error("Failed to create user using email and password!")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                ScreenState.Error("Unable to SignUp. Something went wrong!")
            }
        }
    }
}