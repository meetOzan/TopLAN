package com.gdscedirne.toplan.presentation.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gdscedirne.toplan.R
import com.gdscedirne.toplan.components.CustomAlertDialog
import com.gdscedirne.toplan.components.CustomText
import com.gdscedirne.toplan.ui.theme.DarkRed
import com.gdscedirne.toplan.ui.theme.MainRed
import com.gdscedirne.toplan.ui.theme.khandFamily
import com.gdscedirne.toplan.ui.theme.robatoFamily

@Composable
fun HomeScreen(
    onAction: (HomeAction) -> Unit,
    uiState: HomeUiState
) {

    val (selected, setSelected) = remember {
        mutableIntStateOf(0)
    }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { },
                modifier = Modifier.size(32.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.menu_image),
                    contentDescription = null,
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.toplan_icon),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    colorFilter = ColorFilter.tint(MainRed)
                )
                CustomText(
                    text = stringResource(id = R.string.toplan),
                    fontSize = 32,
                    color = MainRed,
                    fontStyle = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontFamily = khandFamily
                    )
                )
            }
            IconButton(
                onClick = {
                    onAction(HomeAction.ChangeSosDialogState(true))
                },
                modifier = Modifier
                    .size(30.dp, 30.dp)
                    .clip(
                        RoundedCornerShape(0.dp)
                    )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sos_image),
                    contentDescription = null,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
        Image(
            painter = painterResource(id = R.drawable.hub_maps), contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .height(200.dp)
                .clip(
                    RoundedCornerShape(16.dp)
                ),
            contentScale = ContentScale.FillBounds
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 24.dp, top = 8.dp)
        ) {
            items(gridItems.size) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(
                            RoundedCornerShape(16.dp)
                        )
                        .clickable {
                            gridItems[it].onClick()
                        }
                        .background(DarkRed),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = gridItems[it].image),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(vertical = 24.dp)
                    )
                    CustomText(
                        text = gridItems[it].name,
                        fontSize = 16,
                        color = Color.White,
                        fontStyle = TextStyle(
                            fontWeight = FontWeight.Medium,
                            fontFamily = robatoFamily
                        ),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
            }
        }
    }

    if (uiState.sosCallDialog) {
        CustomAlertDialog(
            title = stringResource(R.string.sos_call),
            body = stringResource(R.string.are_you_sure_you_want_to_make_an_emergency_call),
            positiveButtonName = stringResource(R.string.yes),
            negativeButtonName = stringResource(R.string.cancel),
            onDismissClick = { onAction(HomeAction.ChangeSosDialogState(false)) },
            onPositiveAction = {
                makeEmergencyCall(context)
            },
            onNegativeAction = { onAction(HomeAction.ChangeSosDialogState(false)) }
        )
    }
}

fun makeEmergencyCall(context: Context) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:911")
    context.startActivity(intent)
}

data class HomeGridItem(
    val name: String,
    val image: Int,
    val onClick: () -> Unit
)

val gridItems = listOf<HomeGridItem>(
    HomeGridItem("Earthquake", R.drawable.earthquake_hub, {}),
    HomeGridItem("Fire", R.drawable.fire, {}),
    HomeGridItem("Flood", R.drawable.flooded_house, {}),
    HomeGridItem("Snow Avalanche", R.drawable.snow_avalanche, {}),
)

@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen(
        onAction = {},
        uiState = HomeUiState()
    )
}