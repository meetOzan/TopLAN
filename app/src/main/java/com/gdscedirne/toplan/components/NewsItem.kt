package com.gdscedirne.toplan.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gdscedirne.toplan.data.model.News
import com.gdscedirne.toplan.ui.theme.Black
import com.gdscedirne.toplan.ui.theme.DarkGrey
import com.gdscedirne.toplan.ui.theme.LightGrey20
import com.gdscedirne.toplan.ui.theme.interFamily

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NewsItem(
    news: News,
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
            modifier = Modifier
                .background(LightGrey20)
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .padding(
                    top = 4.dp,
                    start = 8.dp,
                    end = 8.dp,
                ),
        ) {
            CoilImage(
                data = news.authorImageUrl,
                modifier = Modifier
                    .padding(
                        start = 2.dp,
                        top = 4.dp,
                    )
                    .clip(RoundedCornerShape(8))
                    .size(36.dp),
                contentScale = ContentScale.Crop
            )
            CustomText(
                text = news.author,
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
            CustomText(
                text = news.date,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .basicMarquee(
                        animationMode = MarqueeAnimationMode.WhileFocused,
                    ),
                color = Black,
                fontStyle = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontFamily = interFamily
                )
            )
        }
        CoilImage(
            data = news.imageUrl,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        CustomText(
            text = news.name,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
                .then(Modifier.padding(bottom = 8.dp, top = 12.dp)),
            fontSize = 16,
            color = Black,
            fontStyle = TextStyle(
                fontWeight = FontWeight.Bold,
                fontFamily = interFamily,
                textAlign = TextAlign.Center
            )
        )
        CustomText(
            text = news.description,
            modifier = Modifier
                .padding(vertical = 12.dp)
                .fillMaxWidth()
                .then(Modifier.padding(bottom = 12.dp)),
            fontSize = 14,
            color = DarkGrey,
            fontStyle = TextStyle(
                fontWeight = FontWeight.Medium,
                fontFamily = interFamily,
                textAlign = TextAlign.Center
            )
        )
    }
}