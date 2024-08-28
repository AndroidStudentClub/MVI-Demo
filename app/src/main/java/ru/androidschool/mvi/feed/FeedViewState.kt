package ru.androidschool.mvi.feed

import ru.androidschool.data.FeedArticle

data class FeedViewState(
    val isLoading: Boolean,
    val articles: List<FeedArticle>,
    val favorites: List<FeedArticle>,
    val error: Throwable?
) {
    companion object {
        // Initial state
        val initial = FeedViewState(
            isLoading = false,
            articles = emptyList(),
            favorites = emptyList(),
            error = null
        )
    }
}