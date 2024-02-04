package com.gdscedirne.toplan.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gdscedirne.toplan.presentation.login.ForgotPasswordScreen
import com.gdscedirne.toplan.presentation.login.LoginViewModel
import com.gdscedirne.toplan.presentation.login.SignInScreen
import com.gdscedirne.toplan.presentation.login.SignUpScreen
import com.gdscedirne.toplan.presentation.login.WelcomeScreen
import com.gdscedirne.toplan.presentation.splash.SplashScreen

@Composable
fun TopLanNavGraph(
    navController: NavHostController,
)  {
    NavHost(
        navController = navController, startDestination = Destinations.SplashDestination.route
    ) {
        splashScreen(
            onWelcomeNavigate = {
                navController.navigate(Destinations.WelcomeDestination.route){
                    popUpTo(Destinations.SplashDestination.route){
                        inclusive = true
                    }
                }
            }
        )
        welcomeScreen(
            onSignInNavigate = {
                navController.navigate(Destinations.SignInDestination.route)
            },
            onSignUpNavigate = {
                navController.navigate(Destinations.SignUpDestination.route)
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
                navController.navigate(Destinations.WelcomeDestination.route){
                    popUpTo(Destinations.WelcomeDestination.route){
                        inclusive = true
                    }
                }
            }
        )
        signUpScreen(
            onSignInNavigate = {
                navController.navigate(Destinations.SignInDestination.route)
            },
            onWelcomeNavigate = {
                navController.navigate(Destinations.WelcomeDestination.route){
                    popUpTo(Destinations.WelcomeDestination.route){
                        inclusive = true
                    }
                }
            }
        )
        forgotPasswordScreen(
            onSignInNavigate = {
                navController.navigate(Destinations.SignInDestination.route){
                    popUpTo(Destinations.ForgotPasswordDestination.route){
                        inclusive = true
                    }
                }
            }
        )
    }
}

fun NavGraphBuilder.splashScreen(
    onWelcomeNavigate: () -> Unit,
) {
    composable(Destinations.SplashDestination.route) {
        SplashScreen(onWelcomeNavigate)
    }
}

fun NavGraphBuilder.welcomeScreen(
    onSignUpNavigate: () -> Unit,
    onSignInNavigate: () -> Unit,
) {
    composable(Destinations.WelcomeDestination.route) {
        WelcomeScreen(
            onSignUpNavigate =  onSignUpNavigate,
            onSignInNavigate = onSignInNavigate,
            onContinueAsGuestNavigate =  {}
        )
    }
}

fun NavGraphBuilder.signInScreen(
    onSignUpNavigate: () -> Unit,
    onForgotPasswordNavigate: () -> Unit,
    onWelcomeNavigate: () -> Unit
) {
    composable(Destinations.SignInDestination.route) {

        val viewModel = hiltViewModel<LoginViewModel>()
        val loginState = viewModel.loginState.collectAsState().value

        SignInScreen(
            onWelcomeNavigate = onWelcomeNavigate,
            onHomeNavigate = {},
            onSignUpNavigate = onSignUpNavigate,
            onForgotPasswordNavigate = onForgotPasswordNavigate,
            loginUiState = loginState,
            onAction = viewModel::onAction
        )
    }
}

fun NavGraphBuilder.signUpScreen(
    onSignInNavigate: () -> Unit,
    onWelcomeNavigate: () -> Unit
) {
    composable(Destinations.SignUpDestination.route) {

        val viewModel = hiltViewModel<LoginViewModel>()
        val loginState = viewModel.loginState.collectAsState().value

        SignUpScreen(
            onWelcomeNavigate = onWelcomeNavigate,
            onSignInNavigate = onSignInNavigate,
            onHomeNavigate = {},
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