package com.gdscedirne.toplan.presentation.chat

sealed class ChatAction{
    data class AskNewQuestion(val newQuestion: String): ChatAction()
    data class ChangeQuestion(val question: String): ChatAction()
}