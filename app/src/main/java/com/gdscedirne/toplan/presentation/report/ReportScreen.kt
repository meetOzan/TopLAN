package com.gdscedirne.toplan.presentation.report

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gdscedirne.toplan.R
import com.gdscedirne.toplan.components.CustomText
import com.gdscedirne.toplan.ui.theme.Black
import com.gdscedirne.toplan.ui.theme.MainRed20
import com.gdscedirne.toplan.ui.theme.interFamily

@Composable
fun ReportScreen() {

    val reportList = listOf(
        ReportItem(
            title = stringResource(R.string.demolished_building),
            body = stringResource(R.string.the_house_i_live_in_has_been_demolished)
        ),
        ReportItem(
            title = stringResource(R.string.supplies_and_equipment),
            body = stringResource(R.string.do_you_or_someone_in_your_neighbourhood_need_supplies_and_equipment)
        ),
        ReportItem(
            title = stringResource(R.string.need_help),
            body = stringResource(R.string.do_you_want_to_report)
        ),
        ReportItem(
            title = stringResource(R.string.gathering_help),
            body = stringResource(R.string.share_assembly_areas)
        )
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MainRed20
    ) {
        Column {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 86.dp),
                elevation = 6.dp,
                backgroundColor = Color.White
            ) {
                Column {
                    CustomText(
                        text = "I'm under the rubble",
                        fontSize = 24,
                        color = Black,
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth(),
                        fontStyle = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontFamily = interFamily,
                            textAlign = TextAlign.Center
                        )
                    )
                    TextButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 24.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MainRed20
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        CustomText(
                            text = stringResource(R.string.send_quick_location),
                            fontSize = 16,
                            color = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            fontStyle = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontFamily = interFamily,
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }
            }
            LazyColumn(content = {
                items(4) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 24.dp, end = 24.dp, top = 16.dp)
                            .clickable {
                                //TODO
                            },
                        elevation = 6.dp,
                        backgroundColor = Color.White,
                    ) {
                        Column {
                            CustomText(
                                text = reportList[it].title,
                                fontSize = 24,
                                color = Black,
                                modifier = Modifier
                                    .padding(top = 16.dp)
                                    .fillMaxWidth(),
                                fontStyle = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = interFamily,
                                    textAlign = TextAlign.Center
                                )
                            )
                            CustomText(
                                text = reportList[it].body,
                                fontSize = 16,
                                color = Black,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp, horizontal = 6.dp),
                                fontStyle = TextStyle(
                                    fontFamily = interFamily,
                                    textAlign = TextAlign.Center
                                )
                            )
                        }
                    }
                }
            })
        }
    }
}


data class ReportItem(
    val title: String,
    val body: String
)

@Preview
@Composable
fun PreviewOfReportScreen() {
    ReportScreen()
}