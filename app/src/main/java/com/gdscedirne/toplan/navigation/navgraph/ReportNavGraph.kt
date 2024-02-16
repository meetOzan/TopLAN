package com.gdscedirne.toplan.navigation.navgraph

import android.content.Intent
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gdscedirne.toplan.R
import com.gdscedirne.toplan.common.ReportOptions
import com.gdscedirne.toplan.navigation.destination.Destinations
import com.gdscedirne.toplan.presentation.home.HomeActivity
import com.gdscedirne.toplan.presentation.report.viewModel.ReportAction
import com.gdscedirne.toplan.presentation.report.ReportItem
import com.gdscedirne.toplan.presentation.report.ReportOptionScreen
import com.gdscedirne.toplan.presentation.report.ReportOptionsScreen
import com.gdscedirne.toplan.presentation.report.viewModel.ReportViewModel

@Composable
fun ReportNavGraph(
    navController: NavHostController
) {

    val context = navController.context

    NavHost(
        navController = navController, startDestination = Destinations.ReportDestination.route
    ) {
        reportScreen(
            onHomeNavigate = {
                val intent = Intent(context, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            },
            onReportNavigate = { option ->
                navController.navigate(Destinations.ReportOptionsDestination.navigateWithArgs(option))
            }
        )
        reportOptionsScreen(
            onHomeNavigate = {
                val intent = Intent(context, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
        )
    }
}

fun NavGraphBuilder.reportScreen(
    onHomeNavigate: () -> Unit,
    onReportNavigate: (String) -> Unit
) {
    composable(
        Destinations.ReportDestination.route
    ) {

        val reportViewModel = hiltViewModel<ReportViewModel>()
        val reportState = reportViewModel.reportState.collectAsState().value

        val reportList = listOf(
            ReportItem(
                title = stringResource(R.string.report_a_disaster),
                body = stringResource(R.string.there_has_been_or_is_about_to_be_a_natural_disaster_here),
                ReportOptions.DISASTER
            ),
            ReportItem(
                title = stringResource(R.string.supplies_and_equipment),
                body = stringResource(R.string.do_you_or_someone_in_your_neighbourhood_need_supplies_and_equipment),
                ReportOptions.SUPPLIES_EQUIPMENT
            ),
            ReportItem(
                title = stringResource(R.string.need_help),
                body = stringResource(R.string.do_you_want_to_report),
                ReportOptions.HELP
            ),
            ReportItem(
                title = stringResource(R.string.gathering_help),
                body = stringResource(R.string.share_assembly_areas),
                ReportOptions.GATHERING_AID
            )
        )

        LaunchedEffect(true) {
            reportViewModel.onAction(ReportAction.GetCurrentDate)
            reportViewModel.onAction(ReportAction.GetCurrentTime)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ReportOptionScreen(
                reportUiState = reportState,
                onReportAction = { action -> reportViewModel.onAction(action) },
                reportList = reportList,
                onHomeNavigate = onHomeNavigate,
                onReportNavigate = onReportNavigate
            )
        }
    }
}

fun NavGraphBuilder.reportOptionsScreen(
    onHomeNavigate: () -> Unit
) {

    composable(
        route = Destinations.ReportOptionsDestination.routeWithArgs,
        arguments = Destinations.ReportOptionsDestination.args
    ) {

        val reportViewModel = hiltViewModel<ReportViewModel>()
        val reportUiState = reportViewModel.reportState.collectAsState().value

        LaunchedEffect(true) {
            reportViewModel.onAction(ReportAction.GetCurrentDate)
            reportViewModel.onAction(ReportAction.GetCurrentTime)
            reportViewModel.onAction(ReportAction.GetDropdownList)
        }

        ReportOptionsScreen(
            onHomeNavigate = onHomeNavigate,
            reportUiState = reportUiState,
            onAction = reportViewModel::onAction,
            dropDownList = reportUiState.dropDownMenu
        )
    }
}