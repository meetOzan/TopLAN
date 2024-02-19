package com.gdscedirne.toplan.presentation.feed

sealed class FeedAction{
    data object LoadFeed: FeedAction()
}