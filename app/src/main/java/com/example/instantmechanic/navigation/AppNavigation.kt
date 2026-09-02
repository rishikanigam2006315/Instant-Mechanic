package com.example.instantmechanic.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.instantmechanic.ui.screen.ForgotPasswordScreen
import com.example.instantmechanic.ui.screen.LoginScreen
import com.example.instantmechanic.ui.screen.MainContainerScreen
import com.example.instantmechanic.ui.screen.MechanicDetailsScreen
import com.example.instantmechanic.ui.screen.OnboardingScreen
import com.example.instantmechanic.ui.screen.RequestServiceScreen
import com.example.instantmechanic.ui.screen.SignUpScreen
import com.example.instantmechanic.ui.screen.SplashScreen
import com.example.instantmechanic.viewmodel.AuthViewModel
import com.example.instantmechanic.viewmodel.MechanicViewModel
import com.example.instantmechanic.viewmodel.ServiceRequestViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val mechanicViewModel: MechanicViewModel = viewModel()
    val serviceRequestViewModel: ServiceRequestViewModel = viewModel()

    val mechanics by mechanicViewModel.mechanics.collectAsState()
    val isLoading by mechanicViewModel.isLoading.collectAsState()
    val error by mechanicViewModel.error.collectAsState()
    val currentUser by authViewModel.currentUser.collectAsState()

    LaunchedEffect(Unit) {
        mechanicViewModel.getMechanics()
    }

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        // SPLASH SCREEN
        composable("splash") {
            SplashScreen(
                onSplashComplete = {
                    navController.navigate("onboarding") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }

        // ONBOARDING SCREEN
        composable("onboarding") {
            OnboardingScreen(
                onFinishOnboarding = {
                    navController.navigate("login") {
                        popUpTo("onboarding") { inclusive = true }
                    }
                }
            )
        }

        // LOGIN SCREEN
        composable("login") {
            LoginScreen(
                authViewModel = authViewModel,
                onLoginSuccess = {
                    navController.navigate("main") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToSignUp = {
                    navController.navigate("signup")
                },
                onNavigateToForgotPassword = {
                    navController.navigate("forgot_password")
                },
                onContinueAsGuest = {
                    navController.navigate("main") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        // SIGN UP SCREEN
        composable("signup") {
            SignUpScreen(
                authViewModel = authViewModel,
                onSignUpSuccess = {
                    navController.navigate("main") {
                        popUpTo("signup") { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        // FORGOT PASSWORD SCREEN
        composable("forgot_password") {
            ForgotPasswordScreen(
                authViewModel = authViewModel,
                onNavigateBackToLogin = {
                    navController.popBackStack()
                }
            )
        }

        // MAIN CONTAINER (Top Bar, Bottom Nav, Drawer, Home, Explore, Bookings, Profile)
        composable("main") {
            MainContainerScreen(
                mechanics = mechanics,
                isLoading = isLoading,
                error = error,
                serviceRequestViewModel = serviceRequestViewModel,
                currentUser = currentUser,
                onUpdateAvatar = { uri -> authViewModel.updateAvatarUri(uri) },
                onUpdateVehicle = { type, plate -> authViewModel.updateVehicle(type, plate) },
                onMechanicClick = { mechanicId ->
                    navController.navigate("details/$mechanicId")
                },
                onEmergencySosClick = { mechanicId ->
                    navController.navigate("request/$mechanicId")
                },
                onLogout = {
                    authViewModel.logout {
                        navController.navigate("login") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                }
            )
        }

        // MECHANIC DETAILS
        composable("details/{mechanicId}") { backStackEntry ->
            val mechanicId = backStackEntry.arguments
                ?.getString("mechanicId")
                ?.toLongOrNull()

            val mechanic = mechanics.find { it.id == mechanicId }

            if (mechanic != null) {
                MechanicDetailsScreen(
                    mechanic = mechanic,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onRequestServiceClick = {
                        navController.navigate("request/${mechanic.id}")
                    }
                )
            }
        }

        // REQUEST SERVICE FORM
        composable("request/{mechanicId}") { backStackEntry ->
            val mechanicId = backStackEntry.arguments
                ?.getString("mechanicId")
                ?.toLongOrNull()

            val mechanic = mechanics.find { it.id == mechanicId }

            if (mechanic != null) {
                RequestServiceScreen(
                    mechanicName = mechanic.name,
                    services = mechanic.services,
                    serviceRequestViewModel = serviceRequestViewModel,
                    currentUser = currentUser,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onViewBookingsClick = {
                        navController.navigate("main") {
                            popUpTo("main") { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}