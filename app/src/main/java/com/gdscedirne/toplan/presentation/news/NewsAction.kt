package com.gdscedirne.toplan.presentation.news

sealed class NewsAction {
    data object GetNews : NewsAction()
}