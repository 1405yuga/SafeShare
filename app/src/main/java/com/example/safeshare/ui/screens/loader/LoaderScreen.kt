package com.example.safeshare.ui.screens.loader

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LoaderScreen(
    toLoginScreen: () -> Unit,
    toMainScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        Button(onClick = { toLoginScreen() }) {
            Text("Login")
        }
        Button(onClick = { toMainScreen }) {
            Text("Main")
        }
    }
}