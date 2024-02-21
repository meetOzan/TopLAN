package com.gdscedirne.toplan.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gdscedirne.toplan.components.CoilImage
import com.gdscedirne.toplan.components.CustomText
import com.gdscedirne.toplan.ui.theme.LightGrey
import com.gdscedirne.toplan.ui.theme.robatoFamily

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier
) {

    Surface(
        color = LightGrey,
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(content = {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Type
                    CustomText(
                        text = "Demolished Building",
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = Color.Black,
                        fontStyle = TextStyle(
                            fontFamily = robatoFamily,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    // TODO Hour
                    CustomText(text = "Reported 9.00")
                }
            }
            item {
                // TODO Image Url
                CoilImage(
                    data = "www.google.com",
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxWidth()
                        .fillMaxHeight(0.3f)
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                )
            }
            item {
                // TODO Description
                CustomText(text = "This is a description")
            }
            item {
                Column {
                    CustomText(text = "Title")
                }
            }
            item {

            }
            item {
                Row {

                }
            }
        })

    }
}

@Preview
@Composable
fun PreviewOfDetail() {
    DetailScreen()
}