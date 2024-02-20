package com.gdscedirne.toplan.presentation.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdscedirne.toplan.common.ResponseState
import com.gdscedirne.toplan.data.model.News
import com.gdscedirne.toplan.domain.repository.TopLanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: TopLanRepository
) : ViewModel() {

    private val _newsUiState = MutableStateFlow(NewsUiState())
    val newsUiState = _newsUiState.asStateFlow()

    fun onAction(action: NewsAction) {
        when (action) {
            NewsAction.GetNews -> getNews()
        }
    }
    private fun getNews() {
        viewModelScope.launch {
            repository.getNews().collect { response ->
                when (response) {
                    is ResponseState.Loading -> {
                        _newsUiState.value = _newsUiState.value.copy(isLoading = true)
                    }
                    is ResponseState.Success -> {
                        _newsUiState.value = _newsUiState.value.copy(
                            isLoading = false,
                            newsList = response.data
                        )
                    }
                    is ResponseState.Error -> {
                        _newsUiState.value = _newsUiState.value.copy(
                            isLoading = false,
                            isError = true,
                            errorMessage = response.message
                        )
                    }
                }
            }
        }
    }
}

data class NewsUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val newsList: List<News> = emptyList(),
)