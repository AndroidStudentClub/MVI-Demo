package ru.androidschool.mvi.feed

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.scan

fun Flow<FeedIntent>.toProcessor(): Flow<FeedProcessor> = map { intent ->
    when (intent) {
        FeedIntent.Initial -> FeedProcessor.LoadArticles
        is FeedIntent.Refresh -> FeedProcessor.LoadArticles
        is FeedIntent.AddToFavorite -> FeedProcessor.AddToFavorite(intent.article.id)
        is FeedIntent.RemoveFromFavorite -> FeedProcessor.RemoveFromFavorite(intent.article.id)
        is FeedIntent.Open -> FeedProcessor.Open(intent.article.id)
    }
}
// Reducer
// Принимает текущий стейт и эффект и возвращает новый стейт
fun Flow<FeedResult>.toViewState(): Flow<FeedViewState> =
    scan(FeedViewState.initial) { acc, result ->
        when (result) {
            FeedResult.Loading -> acc.copy(isLoading = true)
            is FeedResult.ArticlesLoaded -> acc.copy(
                isLoading = false,
                articles = result.articles,
                favorites = result.favorites
            )

            is FeedResult.ArticleAddedToFavorites -> acc.copy(
                favorites = acc.favorites.plus(result.article)
            )

            is FeedResult.ArticleRemovedFromFavorites -> acc.copy(
                favorites = acc.favorites.minus(result.article)
            )

            is FeedResult.Error -> acc.copy(
                isLoading = false,
                error = result.error
            )
        }
    }