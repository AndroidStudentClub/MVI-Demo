package ru.androidschool.mvi.feed

import ru.androidschool.data.FeedArticle

sealed class FeedIntent {

    data object Initial : FeedIntent() // When user just opens the screen.

    class Refresh : FeedIntent()

    class AddToFavorite(val article: FeedArticle) : FeedIntent()

    class RemoveFromFavorite(val article: FeedArticle) : FeedIntent()

    class Open(val article: FeedArticle) : FeedIntent()
}