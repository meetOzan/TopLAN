package com.gdscedirne.toplan.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.gdscedirne.toplan.ui.theme.DarkWhite
import com.gdscedirne.toplan.ui.theme.robatoFamily

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomTextField(
    textTitle: String,
    onValueChange: (String) -> Unit,
    fontFamily: FontFamily = robatoFamily,
    maxLines: Int = 1,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    suffix: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    placerHolder: @Composable (() -> Unit)? = null,
    colors: TextFieldColors = TextFieldDefaults.colors(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        focusedContainerColor = DarkWhite,
        unfocusedContainerColor = DarkWhite,
    ),
    modifier: Modifier = Modifier,
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = textTitle,
        onValueChange = onValueChange,
        textStyle = TextStyle(
            fontFamily = fontFamily
        ),
        readOnly = readOnly,
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon,
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        ),
        keyboardOptions = keyboardOptions,
        suffix = suffix,
        prefix = prefix,
        maxLines = maxLines,
        modifier = modifier,
        placeholder = placerHolder,
        colors = colors,
        shape = RoundedCornerShape(8.dp),
    )
}