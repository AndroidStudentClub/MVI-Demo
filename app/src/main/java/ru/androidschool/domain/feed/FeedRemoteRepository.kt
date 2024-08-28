package ru.androidschool.domain.feed

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.androidschool.data.FeedArticle
import ru.androidschool.data.FeedRepository

class FeedRemoteRepository : FeedRepository {

    override fun addToFavorite(id: Int): Flow<List<FeedArticle>> {
        return flowOf(emptyList())
    }

    override fun loadArticles(): Flow<List<FeedArticle>> {
        val list = mutableListOf<FeedArticle>()
        for (i in 1..10) {
            list.add(FeedArticle(i, "Test Article $i"))
        }
        return flowOf(list)
    }

    override fun loadFavorites(): Flow<List<FeedArticle>> {
        return flowOf(listOf(FeedArticle(2, "Favorite Article")))
    }
}