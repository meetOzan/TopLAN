package com.gdscedirne.toplan.presentation.news

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gdscedirne.toplan.components.CustomLoading
import com.gdscedirne.toplan.components.NewsItem

@Composable
fun NewsScreen(
    modifier : Modifier = Modifier,
    newsUiState: NewsUiState
) {

    if (newsUiState.isLoading) {
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
                items(newsUiState.newsList.size) { index ->
                    NewsItem(
                        news = newsUiState.newsList[index],
                    )
                }
            }
        )
    }

}