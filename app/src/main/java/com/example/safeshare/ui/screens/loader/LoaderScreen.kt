package com.example.safeshare.ui.screens.loader

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.safeshare.utils.screen_state.ScreenState

@Composable
fun LoaderScreen(
    toLoginScreen: () -> Unit,
    toMainScreen: () -> Unit,
    viewModel: LoaderViewModel,
    modifier: Modifier = Modifier
) {
    val screenState by viewModel.screenState.collectAsState()
    LaunchedEffect(screenState) {
        Log.d("LoaderScreen", "state : $screenState")

        when (val state = screenState) {
            is ScreenState.Loaded -> {
                Log.d("LoaderScreen", "state : ${state.result}")

                when (state.result) {
                    UserState.NOT_LOGGED_IN -> {
                        toLoginScreen()
                    }

                    UserState.LOGGED_IN -> {
                        toMainScreen()
                    }
                }
            }

            else -> {}
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        content = {
            when (screenState) {
                is ScreenState.Loaded, is ScreenState.PreLoad, is ScreenState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is ScreenState.Error -> {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Something went wrong!")
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { viewModel.getUser() }) {
                            Text("Retry")
                        }
                    }

                }
            }
        }
    )
}