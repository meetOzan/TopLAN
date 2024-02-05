package com.gdscedirne.toplan.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.gdscedirne.toplan.R
import com.gdscedirne.toplan.common.Constants.bottomNavItems
import com.gdscedirne.toplan.ui.theme.Black
import com.gdscedirne.toplan.ui.theme.MediumGrey
import com.gdscedirne.toplan.ui.theme.robatoFamily

@Composable
fun BottomNav(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination
    BottomNavigation(
        modifier = Modifier
            .height(120.dp)
            .fillMaxWidth(),
        backgroundColor = Color.White,
    ) {
        bottomNavItems.forEach { screen ->
            BottomNavigationItem(
                currentRoute?.hierarchy?.any { screen.route == it.route } == true,
                onClick = {
                    screen.route?.let {
                        navController.navigate(it) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                // Kullanılabilirlik özelliğini kontrol etmek için interactionSource ve indication'ı null olarak ayarla
                icon = {
                    screen.icon?.let {
                        Icon(
                            painter = painterResource(id = it),
                            contentDescription = "",
                            modifier = Modifier
                                .size(24.dp)
                                .padding(bottom = 4.dp)
                        )
                    }
                },
                label = {
                    screen.title?.let {
                        CustomText(
                            text = screen.title,
                            color = MediumGrey,
                            fontSize = 14,
                            fontStyle = TextStyle(
                                fontWeight = FontWeight.Light,
                                fontFamily = robatoFamily,
                            )
                        )
                    }
                },
                selectedContentColor = Black,
                unselectedContentColor = Color.Black.copy(alpha = 0.4f),
            )
        }
    }
}

sealed class Screen(val route: String?, val title: String?, val icon: Int?) {
    data object Home : Screen("home", "Home", R.drawable.home)
    data object Empty : Screen(null, null, null)
    data object Alert : Screen("alert", "Alert", R.drawable.alert)
    data object Profile : Screen("profile", "Profile", R.drawable.profile)
    data object Settings : Screen("settings", "Settings", R.drawable.settings)
}