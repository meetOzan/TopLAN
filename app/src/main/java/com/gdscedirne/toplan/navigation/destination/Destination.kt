package com.gdscedirne.toplan.navigation.destination

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

    object ContactUsDestination : Destination {
        override val route = "contact_us"
    }

    object ProfileDestination : Destination {
        override val route = "profile"
    }

    object SettingsDestination : Destination {
        override val route = "settings"
    }

    object EditProfileDestination : Destination {
        override val route = "edit_profile"
    }

    object ChatDestination : Destination {
        override val route = "chat"
    }

    object FeedDestination : Destination {
        override val route = "feed"
    }
}