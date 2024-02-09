package com.gdscedirne.toplan.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gdscedirne.toplan.presentation.home.HomeScreen
import com.gdscedirne.toplan.presentation.home.HomeViewModel

@Composable
fun TopLanNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController, startDestination = Destinations.HomeDestination.route
    ) {
        homeScreen()
    }
}

fun NavGraphBuilder.homeScreen(
) {
    composable(Destinations.HomeDestination.route) {

        val homeViewModel = hiltViewModel<HomeViewModel>()
        val uiState = homeViewModel.homeState.collectAsState().value

        HomeScreen(
            uiState = uiState,
            onAction = homeViewModel::onAction
        )
    }
}