package com.gdscedirne.toplan.presentation.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdscedirne.toplan.common.ResponseState
import com.gdscedirne.toplan.data.model.Feed
import com.gdscedirne.toplan.data.model.User
import com.gdscedirne.toplan.domain.repository.TopLanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val repository: TopLanRepository
) : ViewModel() {


    private val _feedState = MutableStateFlow(FeedUiState())
    val feedState = _feedState.asStateFlow()

    fun onAction(action: FeedAction) {
        when (action) {
            is FeedAction.LoadFeed -> loadFeed()
            is FeedAction.GetUser -> getUserById(action.userId)
        }
    }

    private fun getUserById(userId: String) {
        viewModelScope.launch {
            repository.getUserById(userId).collect { response ->
                when (response) {
                    is ResponseState.Loading -> {
                        _feedState.value = _feedState.value.copy(isLoading = true)
                    }

                    is ResponseState.Success -> {
                        _feedState.value = _feedState.value.copy(
                            isLoading = false,
                            user = response.data
                        )
                    }

                    is ResponseState.Error -> {
                        _feedState.value = _feedState.value.copy(
                            isLoading = false,
                            isError = true,
                            errorMessage = response.message
                        )
                    }
                }
            }
        }
    }

    private fun loadFeed() {
        viewModelScope.launch {
            repository.getFeed().collect { response ->
                when (response) {
                    is ResponseState.Loading -> {
                        _feedState.value = _feedState.value.copy(isLoading = true)
                    }

                    is ResponseState.Success -> {
                        _feedState.value = _feedState.value.copy(
                            isLoading = false,
                            feedList = response.data
                        )
                    }

                    is ResponseState.Error -> {
                        _feedState.value = _feedState.value.copy(
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

data class FeedUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val feedList: List<Feed> = emptyList(),
    val user: User = User()
)