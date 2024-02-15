package com.gdscedirne.toplan.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gdscedirne.toplan.presentation.report.ReportScreen

@Composable
fun ReportNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController, startDestination = Destinations.ReportDestination.route
    ) {
        reportScreen()
    }
}

fun NavGraphBuilder.reportScreen() {
    composable(Destinations.ReportDestination.route) {
        ReportScreen()
    }
}