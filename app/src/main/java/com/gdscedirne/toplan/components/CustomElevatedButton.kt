package com.gdscedirne.toplan.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
fun CustomElevatedButton(
    onClick: () -> Unit,
    color: ButtonColors = ButtonDefaults.elevatedButtonColors(),
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(8.dp),
    text: @Composable () -> Unit,
    border: BorderStroke? = null,
    elevation: ButtonElevation = ButtonDefaults.buttonElevation(
        defaultElevation = 0.dp,
        pressedElevation = 0.dp
    )
) {
    ElevatedButton(
        onClick = { onClick() },
        modifier = Modifier
            .padding(vertical = 8.dp)
            .then(modifier),
        elevation = elevation,
        border = border,
        colors = color,
        shape = shape,
        content = {
            text()
        }
    )
}