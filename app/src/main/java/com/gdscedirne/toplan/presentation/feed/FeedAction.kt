package com.gdscedirne.toplan.presentation.feed

sealed class FeedAction{
    data object LoadFeed: FeedAction()
    data class GetUser(val userId: String): FeedAction()
}