package ru.androidschool.data

import kotlinx.coroutines.flow.Flow

interface FeedRepository {
    fun addToFavorite(id: Int): Flow<List<FeedArticle>>

    fun loadArticles(): Flow<List<FeedArticle>>

    fun loadFavorites(): Flow<List<FeedArticle>>
}
