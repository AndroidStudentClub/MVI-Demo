package ru.androidschool.mvi.feed

import ru.androidschool.data.FeedArticle

sealed class FeedResult {
    data object Loading : FeedResult()

    class ArticlesLoaded(
        val articles: List<FeedArticle>, val favorites: List<FeedArticle>
    ) : FeedResult()

    class ArticleAddedToFavorites(val article: FeedArticle) : FeedResult()

    class ArticleRemovedFromFavorites(val article: FeedArticle) : FeedResult()

    class Error(val error: Throwable) : FeedResult()
}