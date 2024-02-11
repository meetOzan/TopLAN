package com.gdscedirne.toplan.navigation

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
}