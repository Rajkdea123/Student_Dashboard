package com.example.pw_assignment.presentation.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pw_assignment.domain.usecases.LogoutUseCase
import com.example.pw_assignment.presentation.ui.Screens.DashBoard.HomeScreen
import com.example.pw_assignment.presentation.ui.Screens.NotificationScreen
import com.example.pw_assignment.presentation.ui.Screens.SignIn.SignIn_Screen
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun AppNavHost(navController: NavHostController) {
    val context = LocalContext.current
    val logoutUseCase: LogoutUseCase = koinInject()
    val coroutineScope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = AppScreens.SignIn.route
    ) {
        // Sign In Screen
        composable(AppScreens.SignIn.route) {
            SignIn_Screen(
                onSignButtonInClick = {
                    navController.navigate(AppScreens.Home.route) {
                        popUpTo(AppScreens.SignIn.route) { inclusive = true }
                    }
                },
                navController = navController
            )
        }

        // Home Screen
        composable(AppScreens.Home.route) {
            HomeScreen(
                onNotificationIconClick = {
                    navController.navigate(AppScreens.Notification.route)
                }
            )
        }

        // Notification Screen
        composable(AppScreens.Notification.route) {
            NotificationScreen(
                onBack = {
                    navController.popBackStack()
                },
                onLogoutClick = {
                    // Perform Firebase logout and clear local session
                    coroutineScope.launch {
                        logoutUseCase(context)
                        // Navigate to SignIn and clear backstack
                        navController.navigate(AppScreens.SignIn.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                }
            )
        }
    }
}
