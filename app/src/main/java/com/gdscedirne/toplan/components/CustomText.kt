package com.gdscedirne.toplan.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.gdscedirne.toplan.ui.theme.robatoFamily

@Composable
fun CustomText(
    text: String,
    fontSize: Int = 16,
    color: Color = Color.Black,
    fontStyle: TextStyle = TextStyle(
        fontFamily = robatoFamily,
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Normal,
        textAlign = TextAlign.Start
    ),
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        fontSize = fontSize.sp,
        color = color,
        style = fontStyle,
        modifier = modifier,
    )
}