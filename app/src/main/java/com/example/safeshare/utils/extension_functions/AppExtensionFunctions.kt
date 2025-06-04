package com.example.safeshare.utils.extension_functions

import androidx.navigation.NavController

fun NavController.navigateAndClearPrevious(route: String) {
    this.navigate(route) {
        popUpTo(0) { inclusive = true }
        launchSingleTop = true
    }
}