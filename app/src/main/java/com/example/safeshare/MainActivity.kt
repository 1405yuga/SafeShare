package com.example.safeshare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.safeshare.ui.screens.authentication.login.LoginScreen
import com.example.safeshare.ui.screens.authentication.signup.SignUpScreen
import com.example.safeshare.ui.screens.loader.LoaderScreen
import com.example.safeshare.ui.screens.main.MainScreen
import com.example.safeshare.ui.theme.SafeShareTheme
import com.example.safeshare.utils.extension_functions.navigateAndClearPrevious
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SafeShareTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SafeSecureApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun SafeSecureApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Loader.name,
        modifier = modifier.windowInsetsPadding(WindowInsets.systemBars)
    ) {
        composable(Screen.Loader.name) {
            LoaderScreen(
                toLoginScreen = { navController.navigateAndClearPrevious(Screen.Login.name) },
                toMainScreen = { navController.navigateAndClearPrevious(Screen.MainScreen.name) },
                viewModel = hiltViewModel()
            )
        }
        composable(Screen.Login.name) {
            LoginScreen(
                onLoginClick = { navController.navigateAndClearPrevious(Screen.Loader.name) },
                onSignUpClick = { navController.navigateAndClearPrevious(Screen.SignUp.name) },
                viewModel = hiltViewModel(),
                modifier = modifier
            )
        }
        composable(Screen.SignUp.name) {
            SignUpScreen(
                navToLogin = { navController.navigateAndClearPrevious(Screen.Login.name) },
                modifier = modifier,
                viewModel = hiltViewModel()
            )
        }
        composable(Screen.MainScreen.name) {
            MainScreen()
        }
    }
}

enum class Screen {
    Login, SignUp, Loader, MainScreen
}