package com.gdscedirne.toplan.presentation.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gdscedirne.toplan.R
import com.gdscedirne.toplan.components.CoilImage
import com.gdscedirne.toplan.components.CustomText
import com.gdscedirne.toplan.data.model.Detail
import com.gdscedirne.toplan.data.model.UserComment
import com.gdscedirne.toplan.ui.theme.Black
import com.gdscedirne.toplan.ui.theme.DarkGrey
import com.gdscedirne.toplan.ui.theme.DarkRed20
import com.gdscedirne.toplan.ui.theme.LightGrey
import com.gdscedirne.toplan.ui.theme.MediumGrey20
import com.gdscedirne.toplan.ui.theme.poppinsFamily
import com.gdscedirne.toplan.ui.theme.robatoFamily

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    detail: Detail
) {

    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .background(LightGrey)
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Type
                CustomText(
                    text = detail.type,
                    color = Color.Black,
                    fontSize = 14,
                    fontStyle = TextStyle(
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Bold
                    )
                )
                CustomText(
                    text = stringResource(R.string.reported_detail, detail.time),
                    fontSize = 12,
                    fontStyle = TextStyle(
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.SemiBold
                    ),
                )
            }
            if (detail.image == stringResource(id = R.string.empty)) {
                Spacer(
                    modifier = Modifier
                        .size(8.dp)
                        .fillMaxWidth()
                )
            } else {
                CoilImage(
                    data = detail.image,
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxWidth()
                        .fillMaxHeight(0.2f)
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.location),
                    contentDescription = stringResource(id = R.string.location),
                )
                Column(
                    modifier = Modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    CustomText(
                        text = stringResource(id = R.string.location),
                        color = Color.Black,
                        fontStyle = TextStyle(
                            fontFamily = poppinsFamily,
                            fontWeight = FontWeight.Medium
                        ),
                        fontSize = 12
                    )
                    CustomText(
                        text = detail.date,
                        color = MediumGrey20,
                        fontStyle = TextStyle(
                            fontFamily = poppinsFamily,
                            fontWeight = FontWeight.Medium
                        ),
                        fontSize = 12
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CustomText(
                    text = detail.name,
                    color = Color.Black,
                    fontStyle = TextStyle(
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Medium
                    ),
                    fontSize = 12
                )
                CustomText(
                    text = detail.description,
                    color = MediumGrey20,
                    fontStyle = TextStyle(
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Medium
                    ),
                    fontSize = 12
                )
            }
            Row(
                modifier = Modifier
                    .background(DarkRed20)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomText(
                    text = stringResource(R.string.comments),
                    color = Color.White,
                    fontStyle = TextStyle(
                        fontFamily = robatoFamily
                    ),
                    fontSize = 12,
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    CustomText(
                        text = stringResource(R.string.see_all),
                        color = Color.White,
                        fontSize = 12,
                        modifier = Modifier.padding(end = 6.dp)
                    )
                    Image(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = stringResource(R.string.see_all),
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(16.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
            }

            Column {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.toplan_icon),
                        contentDescription = stringResource(R.string.profile_image),
                        modifier = Modifier
                            .padding(8.dp)
                            .size(48.dp)
                            .clip(CircleShape),
                        alignment = Alignment.TopStart
                    )
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row {
                            Text(
                                text = stringResource(R.string.no_comments_yet),
                                color = Black,
                                style = TextStyle(
                                    fontFamily = robatoFamily,
                                    fontWeight = FontWeight.Medium
                                ),
                                fontFamily = robatoFamily,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .padding(top = 16.dp, end = 16.dp)
                                    .weight(1f)
                                    .basicMarquee(),
                                textAlign = TextAlign.Start
                            )
                            Text(
                                text = "13:02",
                                color = MediumGrey20,
                                style = TextStyle(
                                    fontFamily = robatoFamily,
                                    fontWeight = FontWeight.Medium
                                ),
                                fontFamily = robatoFamily,
                                fontSize = 12.sp,
                                modifier = Modifier
                                    .padding(top = 16.dp, end = 8.dp),
                                textAlign = TextAlign.Start
                            )
                        }
                        CustomText(
                            text = "Be the first to comment",
                            color = DarkGrey,
                            fontSize = 13,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewOfDetail() {
    DetailScreen(
        detail = Detail(
            type = "Demolition",
            time = "2 hours ago",
            description = "This building is demolished, it is not safe to enter the building.",
            image = "https://www.google.com",
            date = "01.04.2024",
            name = "Demolition of the building",
            location = "Istanbul",
            comment = listOf(
                UserComment(
                    comment = "This building is demolished",
                    date = "01.04.2024",
                    time = "13:02",
                    user = "User"
                ),
                UserComment(
                    comment = "This building is demolished",
                    date = "01.04.2024",
                    time = "13:02",
                    user = "User"
                ),
            )
        )
    )
}