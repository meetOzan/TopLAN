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
import com.gdscedirne.toplan.presentation.profile.EditProfileItem
import com.gdscedirne.toplan.presentation.profile.EditProfileScreen
import com.gdscedirne.toplan.presentation.profile.ProfileScreen
import com.gdscedirne.toplan.presentation.profile.viewmodel.ProfileAction
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
        profileScreen(modifier, onEditProfileNavigate = {
            navController.navigate(Destinations.EditProfileDestination.route)
        })
        settingsScreen(modifier, onEditProfileNavigate = {
            navController.navigate(Destinations.EditProfileDestination.route)
        })
        editProfileScreen(
            modifier = modifier,
            onHomeNavigate = {
                navController.navigate(Destinations.HomeDestination.route) {
                    popUpTo(Destinations.HomeDestination.route) {
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
    modifier: Modifier,
    onEditProfileNavigate: () -> Unit
) {
    composable(Destinations.ProfileDestination.route) {

        val profileViewModel = hiltViewModel<ProfileViewModel>()
        val profileUiState = profileViewModel.profileState.collectAsState().value

        LaunchedEffect(true) {
            profileViewModel.onAction(ProfileAction.GetUser)
        }

        ProfileScreen(
            user = profileUiState.user,
            profileUiState = profileUiState,
            modifier = modifier,
            onAction = profileViewModel::onAction,
            onEditProfileNavigate = onEditProfileNavigate
        )

    }
}

fun NavGraphBuilder.settingsScreen(
    modifier: Modifier = Modifier,
    onEditProfileNavigate: () -> Unit
) {
    composable(Destinations.SettingsDestination.route) {

        val viewModel = hiltViewModel<ProfileViewModel>()
        val profileUiState = viewModel.profileState.collectAsState().value

        LaunchedEffect(true) {
            viewModel.onAction(ProfileAction.GetUser)
        }

        LaunchedEffect(profileUiState.user) {
            viewModel.onAction(
                ProfileAction.ChangeEditProfileUser(
                    name = profileUiState.user.name,
                    surname = profileUiState.user.surname,
                    email = profileUiState.user.email,
                    phoneNumber = profileUiState.user.phone,
                    address = profileUiState.user.address,
                    relativeName = profileUiState.user.relativeName
                )
            )
        }

        val profileOptionTitleList = listOf(
            ProfileOption(
                title = stringResource(id = R.string.profile_settings),
                option = stringResource(id = R.string.edit_profile),
                onNavigate = onEditProfileNavigate
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

        SettingsScreen(
            modifier = modifier,
            user = profileUiState.user,
            uiState = profileUiState,
            profileOptionTitleList = profileOptionTitleList
        )
    }
}

fun NavGraphBuilder.editProfileScreen(
    modifier: Modifier = Modifier,
    onHomeNavigate: () -> Unit
) {
    composable(Destinations.EditProfileDestination.route) {

        val viewModel = hiltViewModel<ProfileViewModel>()
        val profileUiState = viewModel.profileState.collectAsState().value

        LaunchedEffect(true) {
            viewModel.onAction(ProfileAction.GetUser)
        }

        LaunchedEffect(profileUiState.user) {
            viewModel.onAction(
                ProfileAction.ChangeEditProfileUser(
                    name = profileUiState.user.name,
                    surname = profileUiState.user.surname,
                    email = profileUiState.user.email,
                    phoneNumber = profileUiState.user.phone,
                    address = profileUiState.user.address,
                    relativeName = profileUiState.user.relativeName
                )
            )
        }

        val list = listOf(
            EditProfileItem(
                title = stringResource(id = R.string.name),
                text = profileUiState.name,
                hint = stringResource(R.string.your_name),
                action = { viewModel.onAction(ProfileAction.ChangeName(it)) }
            ),
            EditProfileItem(
                title = stringResource(R.string.surname),
                text = profileUiState.surname,
                hint = stringResource(R.string.your_surname),
                action = { viewModel.onAction(ProfileAction.ChangeSurname(it)) }
            ),
            EditProfileItem(
                title = stringResource(R.string.phone),
                text = profileUiState.phoneNumber,
                hint = stringResource(id = R.string.your_phone_number),
                action = { viewModel.onAction(ProfileAction.ChangePhoneNumber(it)) }
            ),
            EditProfileItem(
                title = stringResource(R.string.relative_name),
                text = profileUiState.relativeName,
                hint = stringResource(R.string.your_relative_s_full_name),
                action = { viewModel.onAction(ProfileAction.ChangeRelativeName(it)) }
            )
        )

        EditProfileScreen(
            modifier = modifier,
            profileUiState = profileUiState,
            onAction = viewModel::onAction,
            list = list,
            onHomeNavigate = onHomeNavigate
        )

    }
}

