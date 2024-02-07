package com.gdscedirne.toplan.presentation.splash

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.gdscedirne.toplan.R
import com.gdscedirne.toplan.components.CustomText
import com.gdscedirne.toplan.ui.theme.MainRed
import com.gdscedirne.toplan.ui.theme.khandFamily
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onWelcomeScreen: () -> Unit,
    onHomeScreen: () -> Unit
) {

    var rotationState by remember { mutableFloatStateOf(0f) }

    var initialVal by remember { mutableFloatStateOf(0f) }
    var targetVal by remember { mutableFloatStateOf(90f) }

    LaunchedEffect(true) {
        while (true) {
            initialVal += 90f
            targetVal += 90f
            delay(2000)
        }
    }

    // Currently, the splash screen is set to 4 seconds for the demo.
    LaunchedEffect(key1 = true) {
        delay(4000)
        onWelcomeScreen()
    }

    val rotationValue by rememberInfiniteTransition(label = stringResource(R.string.infinitetransition))
        .animateFloat(
            initialValue = initialVal,
            targetValue = targetVal,
            animationSpec = infiniteRepeatable(
                animation = tween(1500),
                repeatMode = RepeatMode.Reverse
            ), label = ""
        )

    rotationState = rotationValue

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MainRed
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.toplan_icon),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth(0.2f)
                    .rotate(
                        rotationState
                    )
            )
            CustomText(
                text = stringResource(id = R.string.toplan),
                color = Color.White,
                fontSize = 48,
                fontStyle = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontFamily = khandFamily
                )
            )
        }
    }
}

@Preview
@Composable
fun PrevSplash() {
    SplashScreen(
        onWelcomeScreen = {},
        onHomeScreen = {}
    )
}