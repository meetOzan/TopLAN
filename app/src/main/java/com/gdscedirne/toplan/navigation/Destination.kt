package com.gdscedirne.toplan.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.gdscedirne.toplan.common.Constants.ARGS_OPTION

interface Destination {
    val route: String
}

object Destinations {
    object SplashDestination : Destination {
        override val route = "splash"
    }

    object WelcomeDestination : Destination {
        override val route = "welcome"
    }

    object SignInDestination : Destination {
        override val route = "sign_in"
    }

    object SignUpDestination : Destination {
        override val route = "sign_up"
    }

    object ForgotPasswordDestination : Destination {
        override val route = "forgot_password"
    }

    object HomeDestination : Destination {
        override val route = "home"
    }

    object EarthQuakeDestination : Destination {
        override val route = "earthquake"
    }

    object ReportDestination : Destination {
        override val route = "report"

    }

    object ReportOptionsDestination : Destination {
        override val route = "report_options"
        fun navigateWithArgs(
            option: String,
        ): String = "${ReportDestination.route}/$option"
        val routeWithArgs = "${ReportDestination.route}/{$ARGS_OPTION}"
        val args = listOf(
            navArgument(ARGS_OPTION) { type = NavType.StringType },
        )
    }

}