package com.gdscedirne.toplan.presentation.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdscedirne.toplan.common.ResponseState
import com.gdscedirne.toplan.domain.repository.TopLanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: TopLanRepository
) : ViewModel() {

    private val _chatState = MutableStateFlow(ChatUiState())
    val chatState = _chatState.asStateFlow()

    fun onAction(action: ChatAction) {
        when (action) {
            is ChatAction.AskNewQuestion -> askNewQuestion(action.newQuestion)
            is ChatAction.ChangeQuestion -> changeQuestion(action.question)
        }
    }

    private fun changeQuestion(question: String) {
        _chatState.value = _chatState.value.copy(question = question)
    }

    private fun askNewQuestion(newQuestion: String) {
        viewModelScope.launch {
            repository.askQuestion(newQuestion).collect { response ->
                when (response) {
                    is ResponseState.Loading -> {
                        _chatState.value = _chatState.value.copy(isLoading = true)
                    }
                    is ResponseState.Error -> {
                        _chatState.value = _chatState.value.copy(isError = true)
                    }
                    is ResponseState.Success -> {
                        _chatState.value = _chatState.value.copy(
                            isLoading = false,
                            lastQuestion = newQuestion,
                            question = "",
                            answer = response.data
                        )
                    }
                }
            }
        }
    }
}

data class ChatUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val question: String = "",
    val answer: String = "",
    val lastQuestion: String = ""
)