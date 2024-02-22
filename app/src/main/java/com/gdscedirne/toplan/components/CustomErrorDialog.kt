package com.gdscedirne.toplan.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gdscedirne.toplan.R
import com.gdscedirne.toplan.ui.theme.DarkRed
import com.gdscedirne.toplan.ui.theme.MainRed
import com.gdscedirne.toplan.ui.theme.MediumGrey40
import com.gdscedirne.toplan.ui.theme.interFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomErrorDialog(
    errorMessage: String,
    onDismissClick: () -> Unit,
    onPositiveAction: () -> Unit,
) {

    BasicAlertDialog(onDismissRequest = { onDismissClick() }) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp, start = 8.dp, end = 8.dp, bottom = 12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MainRed)
                ) {
                    Image(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        modifier = Modifier.padding(10.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
                CustomText(
                    text = stringResource(R.string.error),
                    color = Color.Black,
                    fontSize = 32,
                    modifier = Modifier.padding(top = 16.dp),
                    fontStyle = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontFamily = interFamily,
                        textAlign = TextAlign.Center
                    ),
                )
                Spacer(modifier = Modifier.height(4.dp))
                CustomText(
                    text = errorMessage,
                    color = MediumGrey40,
                    fontSize = 16,
                    fontStyle = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontFamily = interFamily,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.padding(
                        vertical = 16.dp,
                        horizontal = 6.dp,
                    )
                )
                ElevatedButton(
                    onClick = { onPositiveAction() },
                    colors = ButtonDefaults.elevatedButtonColors(containerColor = DarkRed),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    CustomText(
                        text = stringResource(R.string.ok),
                        color = Color.White,
                        fontStyle = TextStyle(
                            fontWeight = FontWeight.Medium,
                            fontFamily = interFamily,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                    )
                }
            }
        }
    }
}


@Preview(
    showBackground = true
)
@Composable
fun PreviewErrorDialog() {
    CustomErrorDialog(
        errorMessage = "Error Message",
        onDismissClick = { },
        onPositiveAction = { }
    )
}