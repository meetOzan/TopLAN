package com.gdscedirne.toplan.presentation.chat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gdscedirne.toplan.R
import com.gdscedirne.toplan.components.CustomText
import com.gdscedirne.toplan.components.CustomTextField
import com.gdscedirne.toplan.ui.theme.DarkGrey
import com.gdscedirne.toplan.ui.theme.DarkWhite
import com.gdscedirne.toplan.ui.theme.MainRed
import com.gdscedirne.toplan.ui.theme.MainRed20
import com.gdscedirne.toplan.ui.theme.interFamily

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    onAction: (ChatAction) -> Unit,
    chatUiState: ChatUiState
) {

    val infiniteTransition = rememberInfiniteTransition(label = "")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing)
        ), label = ""
    )

    val context = LocalContext.current

    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = if (chatUiState.answer == stringResource(R.string.empty))
                    Arrangement.Center else Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (chatUiState.answer == stringResource(R.string.empty)) {
                    CustomText(
                        text = stringResource(R.string.ask_something_like),
                        color = DarkGrey.copy(alpha = 0.6f),
                        fontSize = 18,
                        fontStyle = TextStyle(
                            fontWeight = FontWeight.Medium,
                            fontFamily = interFamily
                        ),
                        modifier = Modifier.padding(16.dp)
                    )
                    LazyColumn(content = {
                        items(3) {
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 24.dp, vertical = 12.dp)
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(DarkWhite)
                                    .clickable {
                                        onAction(
                                            ChatAction.AskNewQuestion(
                                                when (it) {
                                                    0 -> context.getString(R.string.what_should_earthquake_preparedness_kit)
                                                    1 -> context.getString(R.string.what_are_the_safety_measures)
                                                    2 -> context.getString(R.string.where_can_i_apply)
                                                    else -> context.getString(R.string.empty)
                                                }
                                            )
                                        )
                                    },
                                contentAlignment = Alignment.Center,
                            ) {
                                CustomText(
                                    text = when (it) {
                                        0 -> stringResource(R.string.what_should_earthquake_preparedness_kit)
                                        1 -> stringResource(R.string.what_are_the_safety_measures)
                                        2 -> stringResource(R.string.where_can_i_apply)
                                        else -> stringResource(id = R.string.empty)
                                    },
                                    color = DarkGrey.copy(alpha = 0.6f),
                                    fontSize = 16,
                                    fontStyle = TextStyle(
                                        fontWeight = FontWeight.Medium,
                                        fontFamily = interFamily,
                                        textAlign = TextAlign.Center
                                    ),
                                    modifier = Modifier.padding(
                                        vertical = 24.dp,
                                        horizontal = 16.dp
                                    )
                                )
                            }

                        }
                    })
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 8.dp, vertical = 16.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(MainRed20)
                                .align(Alignment.End),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            CustomText(
                                text = chatUiState.lastQuestion,
                                color = Color.White,
                                fontSize = 16,
                                fontStyle = TextStyle(
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = interFamily
                                ),
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxSize(0.85f)
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(DarkWhite)
                                .align(Alignment.Start),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            CustomText(
                                text = chatUiState.answer,
                                color = DarkGrey,
                                fontSize = 16,
                                fontStyle = TextStyle(
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = interFamily
                                ),
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(0.95f)
                            )
                        }

                    }
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                AnimatedVisibility(chatUiState.isLoading) {
                    Image(
                        painter = painterResource(id = R.drawable.toplan_icon),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MainRed),
                        modifier = Modifier
                            .size(24.dp)
                            .fillMaxSize(0.7f)
                            .padding(top = 8.dp)
                            .graphicsLayer {
                                rotationZ = angle
                            },
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 36.dp, top = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomTextField(
                        textTitle = chatUiState.question,
                        onValueChange = { newAnswer ->
                            onAction(ChatAction.ChangeQuestion(newAnswer))
                        },
                        maxLines = 10,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .animateContentSize(),
                        placerHolder = {
                            CustomText(
                                text = stringResource(R.string.enter_a_prompt_here),
                                color = DarkGrey
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
                    )
                    IconButton(
                        onClick = {
                            onAction(ChatAction.AskNewQuestion(chatUiState.question))
                        },
                        modifier = Modifier
                            .padding(start = 6.dp)
                            .size(24.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.send),
                            contentDescription = stringResource(R.string.ask_question),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

        }
    }

}

@Preview
@Composable
fun PreviewOfChat() {
    ChatScreen(onAction = {}, chatUiState = ChatUiState())
}