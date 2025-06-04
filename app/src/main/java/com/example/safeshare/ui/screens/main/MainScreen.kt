package com.example.safeshare.ui.screens.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.CloudUpload
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.safeshare.R
import com.example.safeshare.ui.screens.main.history.HistoryScreen
import com.example.safeshare.ui.screens.main.profile.ProfileScreen
import com.example.safeshare.ui.screens.main.upload.UploadScreen
import com.example.safeshare.utils.annotations.HorizontalScreenPreview
import com.example.safeshare.utils.annotations.VerticalScreenPreview
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    mainViewModel: MainViewModel
) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                NavigationDrawerItem(
                    label = { Text(text = MainScreens.Upload.name) },
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            mainViewModel.onMenuClicked(MainScreens.Upload)
                            navController.navigate(MainScreens.Upload.name)
                        }
                    },
                    selected = mainViewModel.selectedMenu.value == MainScreens.Upload,
                    icon = { Icon(Icons.Outlined.CloudUpload, contentDescription = null) },
                    shape = RectangleShape
                )
                NavigationDrawerItem(
                    label = { Text(text = MainScreens.History.name) },
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            mainViewModel.onMenuClicked(MainScreens.History)
                            navController.navigate(MainScreens.History.name)
                        }
                    },
                    selected = mainViewModel.selectedMenu.value == MainScreens.History,
                    icon = { Icon(Icons.Outlined.History, contentDescription = null) },
                    shape = RectangleShape
                )
                NavigationDrawerItem(
                    label = { Text(text = MainScreens.Profile.name) },
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            mainViewModel.onMenuClicked(MainScreens.Profile)
                            navController.navigate(MainScreens.Profile.name)
                        }
                    },
                    selected = mainViewModel.selectedMenu.value == MainScreens.Profile,
                    icon = { Icon(Icons.Outlined.AccountCircle, contentDescription = null) },
                    shape = RectangleShape
                )
            }
        },
        content = {
            Scaffold(topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.app_name)) },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(Icons.Outlined.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = MainScreens.Upload.name,
                    modifier = Modifier.padding(innerPadding).fillMaxSize()
                ) {
                    composable(route = MainScreens.Upload.name) { UploadScreen() }
                    composable(route = MainScreens.History.name) { HistoryScreen() }
                    composable(route = MainScreens.Profile.name) { ProfileScreen() }
                }
            }
        }
    )
}

@Composable
@VerticalScreenPreview
fun MainScreenVertical() {
    MainScreen(mainViewModel = viewModel())
}

@Composable
@HorizontalScreenPreview
fun MainScreenHorizontal() {
    MainScreen(mainViewModel = viewModel())
}