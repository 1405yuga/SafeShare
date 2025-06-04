package com.example.safeshare.ui.screens.authentication.login

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.safeshare.BuildConfig
import com.example.safeshare.network.AuthRepository
import com.example.safeshare.utils.input_validators.AuthInputValidators
import com.example.safeshare.utils.screen_state.ScreenState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {
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

    fun handleGoogleSignInResult(data: Intent?) {
        _screenState.value = ScreenState.Loading()
        viewModelScope.launch {
            _screenState.value = try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                val account = task.getResult(ApiException::class.java)
                val idToken = account.idToken
                if (idToken == null) {
                    ScreenState.Error("Failed to get ID token")
                } else {
                    val authResult = authRepository.signInWithGoogle(idToken = idToken)
                    if (authResult.user != null) {
                        ScreenState.Loaded("Google Signed successfully")
                    } else {
                        ScreenState.Error("Google Sign-In failed!")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                ScreenState.Error("Google Sign in failed : ${e.message}")
            }
        }
    }

    fun getGoogleSignInIntent(): Intent {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.WEB_CLIENT)
            .requestEmail()
            .build()

        val signInClient = GoogleSignIn.getClient(context, gso)
        return signInClient.signInIntent
    }
}