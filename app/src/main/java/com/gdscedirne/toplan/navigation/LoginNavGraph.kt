package com.gdscedirne.toplan.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gdscedirne.toplan.HomeActivity
import com.gdscedirne.toplan.presentation.login.ForgotPasswordScreen
import com.gdscedirne.toplan.presentation.login.LoginViewModel
import com.gdscedirne.toplan.presentation.login.SignInScreen
import com.gdscedirne.toplan.presentation.login.SignUpScreen
import com.gdscedirne.toplan.presentation.login.WelcomeScreen
import com.gdscedirne.toplan.presentation.splash.SplashAction
import com.gdscedirne.toplan.presentation.splash.SplashScreen
import com.gdscedirne.toplan.presentation.splash.SplashViewModel

@Composable
fun LoginNavGraph(
    navController: NavHostController,
) {

    val context = LocalContext.current

    NavHost(
        navController = navController, startDestination = Destinations.SplashDestination.route
    ) {
        splashScreen(
            onWelcomeNavigate = {
                navController.navigate(Destinations.WelcomeDestination.route) {
                    popUpTo(Destinations.SplashDestination.route) {
                        inclusive = true
                    }
                }
            },
            onHomeNavigate = {
                val intent = Intent(context, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
        )
        welcomeScreen(
            onSignInNavigate = {
                navController.navigate(Destinations.SignInDestination.route)
            },
            onSignUpNavigate = {
                navController.navigate(Destinations.SignUpDestination.route)
            },
            onHomeNavigate = {
                val intent = Intent(context, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
        )
        signInScreen(
            onSignUpNavigate = {
                navController.navigate(Destinations.SignUpDestination.route)
            },
            onForgotPasswordNavigate = {
                navController.navigate(Destinations.ForgotPasswordDestination.route)
            },
            onWelcomeNavigate = {
                navController.navigate(Destinations.WelcomeDestination.route) {
                    popUpTo(Destinations.WelcomeDestination.route) {
                        inclusive = true
                    }
                }
            },
            onHomeNavigate = {
                val intent = Intent(context, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
        )
        signUpScreen(
            onSignInNavigate = {
                navController.navigate(Destinations.SignInDestination.route)
            },
            onWelcomeNavigate = {
                navController.navigate(Destinations.WelcomeDestination.route) {
                    popUpTo(Destinations.WelcomeDestination.route) {
                        inclusive = true
                    }
                }
            },
            onHomeNavigate = {
                val intent = Intent(context, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
        )
        forgotPasswordScreen(
            onSignInNavigate = {
                navController.navigate(Destinations.SignInDestination.route) {
                    popUpTo(Destinations.ForgotPasswordDestination.route) {
                        inclusive = true
                    }
                }
            }
        )
    }
}

fun NavGraphBuilder.splashScreen(
    onWelcomeNavigate: () -> Unit,
    onHomeNavigate: () -> Unit
) {
    composable(Destinations.SplashDestination.route) {

        val splashViewModel = hiltViewModel<SplashViewModel>()
        val splashUiState = splashViewModel.splashUiState.collectAsState().value

        LaunchedEffect(true) {
            splashViewModel.onAction(SplashAction.CheckUserSession)
        }

        SplashScreen(onWelcomeNavigate, onHomeNavigate, splashUiState)
    }
}

fun NavGraphBuilder.welcomeScreen(
    onSignUpNavigate: () -> Unit,
    onSignInNavigate: () -> Unit,
    onHomeNavigate: () -> Unit
) {
    composable(Destinations.WelcomeDestination.route) {
        WelcomeScreen(
            onSignUpNavigate = onSignUpNavigate,
            onSignInNavigate = onSignInNavigate,
            onContinueAsGuestNavigate = onHomeNavigate
        )
    }
}

fun NavGraphBuilder.signInScreen(
    onSignUpNavigate: () -> Unit,
    onForgotPasswordNavigate: () -> Unit,
    onWelcomeNavigate: () -> Unit,
    onHomeNavigate: () -> Unit
) {
    composable(Destinations.SignInDestination.route) {

        val viewModel = hiltViewModel<LoginViewModel>()
        val loginState = viewModel.loginState.collectAsState().value

        SignInScreen(
            onWelcomeNavigate = onWelcomeNavigate,
            onHomeNavigate = onHomeNavigate,
            onSignUpNavigate = onSignUpNavigate,
            onForgotPasswordNavigate = onForgotPasswordNavigate,
            loginUiState = loginState,
            onAction = viewModel::onAction
        )
    }
}

fun NavGraphBuilder.signUpScreen(
    onSignInNavigate: () -> Unit,
    onWelcomeNavigate: () -> Unit,
    onHomeNavigate: () -> Unit
) {
    composable(Destinations.SignUpDestination.route) {

        val viewModel = hiltViewModel<LoginViewModel>()
        val loginState = viewModel.loginState.collectAsState().value

        SignUpScreen(
            onWelcomeNavigate = onWelcomeNavigate,
            onSignInNavigate = onSignInNavigate,
            onHomeNavigate = onHomeNavigate,
            loginUiState = loginState,
            onAction = viewModel::onAction
        )
    }
}

fun NavGraphBuilder.forgotPasswordScreen(
    onSignInNavigate: () -> Unit,
) {
    composable(Destinations.ForgotPasswordDestination.route) {

        val viewModel = hiltViewModel<LoginViewModel>()
        val loginState = viewModel.loginState.collectAsState().value

        ForgotPasswordScreen(
            onSignInNavigate = onSignInNavigate,
            loginUiState = loginState,
            viewModel::onAction
        )
    }
}