package com.gdscedirne.toplan.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gdscedirne.toplan.R
import com.gdscedirne.toplan.ui.theme.Black
import com.gdscedirne.toplan.ui.theme.LightGrey10
import com.gdscedirne.toplan.ui.theme.MainRed
import com.gdscedirne.toplan.ui.theme.MediumGrey10
import com.gdscedirne.toplan.ui.theme.robatoFamily

@Composable
fun MarkerWindow(
    markerIcon: Int,
    title: String,
    description: String,
    reportedTime: String,
    reportedDate: String,
    type: String,
    location: String,
    onAction: () -> Unit = {}
) {

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .background(Color.Transparent)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(MainRed),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CustomText(
                type,
                14,
                color = Color.White,
                modifier = Modifier.padding(top = 16.dp, start = 8.dp, bottom = 16.dp)
            )
            CustomText(
                stringResource(R.string.reported, reportedTime),
                14,
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(Color.White)
        ) {
            Row(
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = markerIcon),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(vertical = 16.dp, horizontal = 8.dp)
                        .drawWithContent {
                            drawCircle(color = LightGrey10, radius = 56f, center = center)
                            this.drawContent()
                        },
                    colorFilter = ColorFilter.tint(MainRed),
                )
                Column(
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    CustomText(
                        if (location == stringResource(id = R.string.empty)) location
                        else stringResource(R.string.emergency_location), 13, color = Black)
                    CustomText(reportedDate, 13, color = MediumGrey10)
                }
            }
            CustomText(
                text = title,
                13,
                color = Black,
                modifier = Modifier.padding(vertical = 4.dp),
                fontStyle =
                TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontFamily = robatoFamily
                )
            )
            CustomText(
                text = description,
                13,
                color = Black
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = {
                    onAction()
                },
                modifier = Modifier
            ) {
                CustomText(
                    text = stringResource(R.string.view_details),
                    fontSize = 14,
                    color = MainRed,
                    fontStyle = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontFamily = robatoFamily
                    )
                )
            }
            IconButton(
                onClick = {
                    onAction()
                },
                modifier = Modifier.size(24.dp, 24.dp)
            ) {
                Image(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(MainRed)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewOfWindow() {
    MarkerWindow(
        markerIcon = R.drawable.structure,
        title = "Collapsed Building",
        description = "This is a collapsed building is going to be fall down",
        reportedTime = "18:82",
        location = "Los Angeles",
        reportedDate = "18.08.2022",
        type = "Collapsed Building"
    )
}