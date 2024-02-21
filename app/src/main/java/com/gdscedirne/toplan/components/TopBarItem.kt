package com.gdscedirne.toplan.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.gdscedirne.toplan.R
import com.gdscedirne.toplan.ui.theme.Black
import com.gdscedirne.toplan.ui.theme.MainRed

@Composable
fun TopBarItem(
    image: Int,
    text: String,
    isSelected: Boolean
) {
    Card(
        colors = CardDefaults.cardColors(
            if (isSelected) MainRed else Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (image != R.drawable.transparent){
                Icon(
                    painter = painterResource(id = image),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
            CustomText(
                text = text,
                color = if (isSelected) Color.White else Black,

            )
        }
    }
}