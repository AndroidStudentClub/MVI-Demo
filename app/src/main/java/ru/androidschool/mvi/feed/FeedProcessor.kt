package ru.androidschool.mvi.feed

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import ru.androidschool.data.FeedArticle
import ru.androidschool.data.FeedRepository
import ru.androidschool.domain.feed.FeedRemoteRepository

sealed class FeedProcessor {
    var repository: FeedRepository = FeedRemoteRepository()
    abstract suspend fun process(): Flow<FeedResult>

    data object LoadArticles : FeedProcessor() {
        override suspend fun process(): Flow<FeedResult> {
            return combine<List<FeedArticle>, List<FeedArticle>, FeedResult>(
                repository.loadArticles(),
                repository.loadFavorites()
            ) { articles, favorites ->
                FeedResult.ArticlesLoaded(articles, favorites)
            }.onStart {
                emit(FeedResult.Loading)
            }.catch {
                emit(FeedResult.Error(it))
            }
        }
    }

    class AddToFavorite(private val id: Int) : FeedProcessor() {
        override suspend fun process(): Flow<FeedResult> =
            repository.addToFavorite(id)
                .map { articles ->
                    FeedResult.ArticleAddedToFavorites(articles[0]) as FeedResult
                }
                .onStart {
                    emit(FeedResult.Loading)
                }
                .catch {
                    emit(FeedResult.Error(it))
                }
    }

    class RemoveFromFavorite(val id: Int) : FeedProcessor() {
        override suspend fun process(): Flow<FeedResult> {
            TODO("Not yet implemented")
        }
    }

    class Open(val id: Int) : FeedProcessor() {
        override suspend fun process(): Flow<FeedResult> {
            TODO("Not yet implemented")
        }
    }
}