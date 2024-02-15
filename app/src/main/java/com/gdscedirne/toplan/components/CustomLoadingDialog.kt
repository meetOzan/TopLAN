package com.gdscedirne.toplan.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.gdscedirne.toplan.R
import com.gdscedirne.toplan.ui.theme.MainRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomLoading() {

    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing)
        ), label = ""
    )

    AlertDialog(
        onDismissRequest = { }) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.toplan_icon),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MainRed),
                modifier = Modifier
                    .fillMaxSize(0.7f)
                    .graphicsLayer {
                        rotationZ = angle
                    }
            )
        }
    }
}

@Preview
@Composable
fun CustomLoadingPreview() {
    CustomLoading()
}