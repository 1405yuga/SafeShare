package com.example.safeshare.ui.screens.loader

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.safeshare.network.AuthRepository
import com.example.safeshare.utils.screen_state.ScreenState
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoaderViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {
    private val _screenState = MutableStateFlow<ScreenState<UserState>>(ScreenState.PreLoad())
    val screenState: StateFlow<ScreenState<UserState>> = _screenState

    init {
        getUser()
    }

    fun getUser() {
        _screenState.value = ScreenState.Loading()
        viewModelScope.launch {
            _screenState.value = try {
                val res: FirebaseUser? = authRepository.getCurrentUser()
                Log.d(this.javaClass.simpleName, "Current user : $res")
                if (res == null) {
                    ScreenState.Loaded(UserState.NOT_LOGGED_IN)
                } else {
                    ScreenState.Loaded(UserState.LOGGED_IN)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                ScreenState.Error("Unable to load")
            }
        }
    }
}

enum class UserState {
    NOT_LOGGED_IN,
    LOGGED_IN
}