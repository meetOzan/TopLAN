package com.gdscedirne.toplan.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gdscedirne.toplan.R
import com.gdscedirne.toplan.ui.theme.DarkRed
import com.gdscedirne.toplan.ui.theme.LightGrey20
import com.gdscedirne.toplan.ui.theme.MainRed
import com.gdscedirne.toplan.ui.theme.MediumGrey40
import com.gdscedirne.toplan.ui.theme.interFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAlertDialog(
    title: String,
    body: String,
    positiveButtonName: String,
    negativeButtonName: String,
    onDismissClick: () -> Unit,
    onPositiveAction: () -> Unit,
    onNegativeAction: () -> Unit,
) {

    BasicAlertDialog(
        onDismissRequest = { onDismissClick() }) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp, horizontal = 8.dp),
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
                        painter = painterResource(id = R.drawable.notification),
                        contentDescription = null,
                        modifier = Modifier.padding(10.dp)
                    )
                }
                CustomText(
                    text = title,
                    color = Color.Black,
                    fontSize = 24,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                    fontStyle = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontFamily = interFamily,
                        textAlign = TextAlign.Center
                    ),
                )
                Spacer(modifier = Modifier.height(4.dp))
                CustomText(
                    text = body,
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    ElevatedButton(
                        onClick = { onNegativeAction() },
                        colors = ButtonDefaults.elevatedButtonColors(containerColor = LightGrey20),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        CustomText(
                            text = negativeButtonName,
                            color = Color.Black,
                            fontStyle = TextStyle(
                                fontWeight = FontWeight.Medium,
                                fontFamily = interFamily,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                        )
                    }
                    ElevatedButton(
                        onClick = { onPositiveAction() },
                        colors = ButtonDefaults.elevatedButtonColors(containerColor = DarkRed),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        CustomText(
                            text = positiveButtonName,
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
}

@Preview(
    showBackground = true
)
@Composable
fun PreviewAlertDialog() {
    CustomAlertDialog(
        title = "Are you sure?",
        body = "Body",
        positiveButtonName = "Yes",
        negativeButtonName = "No",
        onDismissClick = { },
        onPositiveAction = { },
        onNegativeAction = { }
    )
}