@file:OptIn(ExperimentalFoundationApi::class)

package com.gdscedirne.toplan.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gdscedirne.toplan.R
import com.gdscedirne.toplan.data.model.Feed
import com.gdscedirne.toplan.ui.theme.Black
import com.gdscedirne.toplan.ui.theme.DarkGrey
import com.gdscedirne.toplan.ui.theme.LightGrey
import com.gdscedirne.toplan.ui.theme.interFamily

@Composable
fun FeedItem(
    modifier: Modifier = Modifier,
    feed: Feed
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(
                    top = 4.dp,
                    start = 8.dp,
                    end = 8.dp,
                ),
        ) {
            CoilImage(
                data = feed.user.get(key = stringResource(R.string.imageurl)) as String,
                modifier = Modifier
                    .padding(
                        start = 2.dp,
                        top = 4.dp,
                    )
                    .size(48.dp)
                    .clip(
                        CircleShape
                    )
                    .drawBehind {
                        this.drawCircle(
                            color = DarkGrey,
                            radius = 48f,
                            center = center
                        )
                    }
            )
            CustomText(
                text = "${
                    feed.user.get(key = stringResource(id = R.string.small_name))
                } ${
                    feed.user.get(key = stringResource(id = R.string.small_surname))
                }",
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(1f)
                    .basicMarquee(
                        animationMode = MarqueeAnimationMode.WhileFocused,
                    ),
                color = Black,
                fontStyle = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontFamily = interFamily
                )
            )
            IconButton(
                onClick = { },
                modifier = Modifier
                    .width(24.dp)
                    .height(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(R.string.more),
                    tint = DarkGrey
                )
            }
        }
        CoilImage(
            data = feed.imageUrl,
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        )
        CustomText(
            text = feed.description,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
                .then(Modifier.padding(bottom = 8.dp, top = 12.dp)),
            fontSize = 16,
            color = Black,
            fontStyle = TextStyle(
                fontWeight = FontWeight.Medium,
                fontFamily = interFamily,
                textAlign = TextAlign.Center
            )
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth(),
            color = LightGrey
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.like),
                        contentDescription = stringResource(R.string.like),
                        colorFilter = ColorFilter.tint(DarkGrey)
                    )
                }
                CustomText(
                    text = feed.likeCount.toString(),
                    modifier = Modifier.padding(start = 4.dp),
                    color = Black
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.comment),
                        contentDescription = stringResource(R.string.comment),
                        colorFilter = ColorFilter.tint(DarkGrey)
                    )
                }
                CustomText(
                    text = feed.comments.size.toString(),
                    modifier = Modifier.padding(start = 4.dp),
                    color = DarkGrey
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewOfFeedItem() {
    FeedItem(
        feed = Feed(
            id = "1",
            title = "Title",
            imageUrl = "https://www.google.com",
            description = "Description"
        )
    )
}