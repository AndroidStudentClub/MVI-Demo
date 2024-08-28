package ru.androidschool.domain.roxie

import io.reactivex.Single
import kotlinx.coroutines.flow.flowOf
import ru.androidschool.data.FeedArticle

interface ArticlesRepository {
    fun getAllArticlesSingle(): Single<List<FeedArticle>>
}

class ArticlesRepositoryImpl : ArticlesRepository {


    override fun getAllArticlesSingle(): Single<List<FeedArticle>> {
        val list = mutableListOf<FeedArticle>()
        for (i in 1..10) {
            list.add(FeedArticle(i, "Test Article $i"))
        }

        return Single.just(list)
    }
}