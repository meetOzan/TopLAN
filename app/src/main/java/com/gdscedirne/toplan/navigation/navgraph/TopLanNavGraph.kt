package com.gdscedirne.toplan.navigation.navgraph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gdscedirne.toplan.R
import com.gdscedirne.toplan.navigation.destination.Destinations
import com.gdscedirne.toplan.presentation.contact_us.ContactUsScreen
import com.gdscedirne.toplan.presentation.earthquake.EarthQuakeScreen
import com.gdscedirne.toplan.presentation.earthquake.EarthquakeAction
import com.gdscedirne.toplan.presentation.earthquake.EarthquakeViewModel
import com.gdscedirne.toplan.presentation.home.HomeScreen
import com.gdscedirne.toplan.presentation.profile.ProfileScreen
import com.gdscedirne.toplan.presentation.profile.viewmodel.ProfileOnAction
import com.gdscedirne.toplan.presentation.profile.viewmodel.ProfileViewModel
import com.gdscedirne.toplan.presentation.settings.ProfileOption
import com.gdscedirne.toplan.presentation.settings.SettingsScreen

@Composable
fun TopLanNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
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
                navController.navigate(Destinations.HomeDestination.route) {
                    popUpTo(Destinations.HomeDestination.route) {
                        inclusive = true
                    }
                }
            }
        )
        contactUsScreen(
            onHomeNavigate = {
                navController.navigate(Destinations.HomeDestination.route) {
                    popUpTo(Destinations.HomeDestination.route) {
                        inclusive = true
                    }
                }
            }
        )
        profileScreen(modifier)
        settingsScreen(modifier)
    }
}

fun NavGraphBuilder.homeScreen(
    onEarthQuakeNavigate: () -> Unit
) {
    composable(Destinations.HomeDestination.route) {

        HomeScreen(
            onEarthQuakeNavigate = onEarthQuakeNavigate
        )
    }
}

fun NavGraphBuilder.earthquakeScreen(
    onHomeNavigate: () -> Unit
) {
    composable(Destinations.EarthQuakeDestination.route) {

        val earthquakeViewModel = hiltViewModel<EarthquakeViewModel>()
        val uiState = earthquakeViewModel.earthquakeUiState.collectAsState().value

        LaunchedEffect(key1 = true) {
            earthquakeViewModel.onAction(EarthquakeAction.GetMarkers)
        }

        EarthQuakeScreen(
            onHomeNavigate = onHomeNavigate,
            onAction = earthquakeViewModel::onAction,
            earthquakeUiState = uiState
        )
    }
}

fun NavGraphBuilder.contactUsScreen(
    onHomeNavigate: () -> Unit
) {
    composable(Destinations.ContactUsDestination.route) {
        ContactUsScreen {
            onHomeNavigate()
        }
    }
}

fun NavGraphBuilder.profileScreen(
    modifier: Modifier
) {
    composable(Destinations.ProfileDestination.route) {

        val profileViewModel = hiltViewModel<ProfileViewModel>()
        val profileUiState = profileViewModel.profileState.collectAsState().value

        LaunchedEffect(true) {
            profileViewModel.onAction(ProfileOnAction.GetUser)
        }

        ProfileScreen(
            user = profileUiState.user,
            profileUiState = profileUiState,
            modifier = modifier,
            onAction = profileViewModel::onAction,
            onEditProfileNavigate = {}
        )

    }
}

fun NavGraphBuilder.settingsScreen(
    modifier : Modifier = Modifier
) {
    composable(Destinations.SettingsDestination.route) {

        val viewModel = hiltViewModel<ProfileViewModel>()
        val profileUiState = viewModel.profileState.collectAsState().value

        LaunchedEffect(true) {
            viewModel.onAction(ProfileOnAction.GetUser)
        }

        val profileOptionTitleList = listOf(
            ProfileOption(
                title = stringResource(id = R.string.profile_settings),
                option = stringResource(id = R.string.edit_profile),
                onNavigate = {}
            ),
            ProfileOption(
                title = stringResource(id = R.string.security_settings),
                option = stringResource(id = R.string.change_password),
                onNavigate = {}
            ),
            ProfileOption(
                title = stringResource(id = R.string.agreements),
                option = stringResource(id = R.string.privacy_policy),
                onNavigate = {}
            )
        )

        SettingsScreen(modifier = modifier, user = profileUiState.user, profileOptionTitleList = profileOptionTitleList)
    }
}

