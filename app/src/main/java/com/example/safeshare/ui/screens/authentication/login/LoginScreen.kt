package com.example.safeshare.ui.screens.authentication.login

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.example.safeshare.R
import com.example.safeshare.utils.composables.ShowAndHidePasswordTextField
import com.example.safeshare.utils.composables.TextFieldWithErrorText
import com.example.safeshare.utils.screen_state.ScreenState

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
    viewModel: LoginViewModel,
    modifier: Modifier = Modifier
) {
    val screenState by viewModel.screenState.collectAsState()
    val currentContext = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.handleGoogleSignInResult(result.data)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(R.dimen.large_padding))
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
            ),
            modifier = Modifier.padding(dimensionResource(R.dimen.small_padding))
        )
        Text(
            text = "Login to continue managing your files",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.large_padding))
        )
        TextFieldWithErrorText(
            label = "Email",
            value = viewModel.email,
            onTextChange = { viewModel.onEmailChange(it) },
            errorMsg = viewModel.emailError
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.small_spacer_height)))
        ShowAndHidePasswordTextField(
            label = "Password",
            password = viewModel.password,
            onTextChange = { viewModel.onPasswordChange(it) },
            showPassword = viewModel.showPassword,
            onShowPasswordClick = { viewModel.togglePasswordVisibility() },
            errorMsg = viewModel.passwordError
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.medium_spacer_height)))
        Button(
            onClick = {
                viewModel.emailLogin()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.min_clickable_size)),
            shape = RoundedCornerShape(dimensionResource(R.dimen.button_radius)),
            enabled = screenState !is ScreenState.Loading,
        ) {
            Text(
                text = when (screenState) {
                    is ScreenState.Loading -> "Loading.."
                    else -> "Get started"
                }
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.large_spacer_height)))
        Row(verticalAlignment = Alignment.CenterVertically) {
            HorizontalDivider(modifier = Modifier.weight(1f))
            Text(
                text = "  or  ",
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            HorizontalDivider(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.medium_spacer_height)))
        OutlinedButton(
            onClick = {
                // TODO: google login
                val intent = viewModel.getGoogleSignInIntent()
                launcher.launch(intent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.min_clickable_size)),
            shape = RoundedCornerShape(dimensionResource(R.dimen.button_radius))
        ) {
            Icon(
                painter = painterResource(R.drawable.google_icon),
                contentDescription = "Localized description",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(text = "Log in with Google")
        }
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.medium_spacer_height)))
        Row(verticalAlignment = Alignment.CenterVertically) {
            HorizontalDivider(modifier = Modifier.weight(1f))
            Text(
                text = "  or  ",
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            HorizontalDivider(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.medium_spacer_height)))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Don't have an account? ")
            Text(
                text = "Sign up",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.clickable { onSignUpClick() }
            )
        }

        LaunchedEffect(screenState) {
            when (val state = screenState) {
                is ScreenState.Error -> Toast.makeText(
                    currentContext,
                    state.message,
                    Toast.LENGTH_LONG
                ).show()

                is ScreenState.Loaded -> {
                    onLoginClick()
                }

                else -> {}
            }
        }
    }
}

//@VerticalScreenPreview
//@Composable
//fun LoginScreenVertical() {
//    LoginScreen(
//        onLoginClick = {},
//        onSignUpClick = {},
//        viewModel = LoginViewModel(
//            authRepository = AuthRepository(
//                firebaseAuth = TODO()
//            ),
//            context = TODO()
//        )
//    )
//}

//@HorizontalScreenPreview
//@Composable
//fun LoginScreenHorizontal() {
//    LoginScreen(
//        onLoginClick = {},
//        onSignUpClick = {},
//        viewModel = LoginViewModel()
//    )
//}