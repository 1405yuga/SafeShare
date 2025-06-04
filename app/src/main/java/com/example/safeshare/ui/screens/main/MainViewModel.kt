package com.example.safeshare.ui.screens.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    //    private val _selectedScreen = MutableStateFlow(MainScreens.Upload)
//    val selectedScreen: StateFlow<MainScreens> = _selectedScreen
    var selectedMenu = mutableStateOf(MainScreens.Upload)
        private set

    fun onMenuClicked(mainScreens: MainScreens) {
        selectedMenu.value = mainScreens
    }
}

enum class MainScreens {
    Upload, History, Profile
}