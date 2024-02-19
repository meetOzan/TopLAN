package com.gdscedirne.toplan.presentation.feed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gdscedirne.toplan.components.CustomLoading
import com.gdscedirne.toplan.components.FeedItem

@Composable
fun FeedScreen(
    modifier: Modifier = Modifier,
    feedUiState: FeedUiState,
    onAction: (FeedAction) -> Unit
) {

    if (feedUiState.isLoading) {
        CustomLoading()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            content = {
                items(feedUiState.feedList.size) { index ->
                    FeedItem(
                        modifier = modifier,
                        feed = feedUiState.feedList[index],
                    )
                }
            }
        )
    }

}