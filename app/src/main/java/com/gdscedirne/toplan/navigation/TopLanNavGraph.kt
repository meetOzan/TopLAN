package com.gdscedirne.toplan.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gdscedirne.toplan.presentation.home.HomeScreen

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
        HomeScreen()
    }
}