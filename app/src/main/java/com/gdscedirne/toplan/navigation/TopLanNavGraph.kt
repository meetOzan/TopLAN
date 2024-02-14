package com.gdscedirne.toplan.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gdscedirne.toplan.presentation.earthquake.EarthQuakeScreen
import com.gdscedirne.toplan.presentation.earthquake.EarthquakeViewModel
import com.gdscedirne.toplan.presentation.home.HomeScreen
import com.gdscedirne.toplan.presentation.home.HomeViewModel

@Composable
fun TopLanNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController, startDestination = Destinations.HomeDestination.route
    ) {
        homeScreen(
            onEarthQuakeNavigate = {
                navController.navigate(Destinations.EarthQuakeDestination.route)
            }
        )
        earthquakeScreen(
            onHomeNavigate = {
                navController.navigate(Destinations.HomeDestination.route){
                    popUpTo(Destinations.HomeDestination.route){
                        inclusive = true
                    }
                }
            }
        )
    }
}

fun NavGraphBuilder.homeScreen(
    onEarthQuakeNavigate: () -> Unit
) {
    composable(Destinations.HomeDestination.route) {

        val homeViewModel = hiltViewModel<HomeViewModel>()
        val uiState = homeViewModel.homeState.collectAsState().value

        HomeScreen(
            onEarthQuakeNavigate = onEarthQuakeNavigate
        )
    }
}

fun NavGraphBuilder.earthquakeScreen(
    onHomeNavigate : () -> Unit
) {
    composable(Destinations.EarthQuakeDestination.route) {

        val earthquakeViewModel = hiltViewModel<EarthquakeViewModel>()
        val uiState = earthquakeViewModel.earthquakeUiState.collectAsState().value

        EarthQuakeScreen(
            onHomeNavigate = onHomeNavigate,
            onAction = earthquakeViewModel::onAction,
            earthquakeUiState = uiState
        )
    }
}