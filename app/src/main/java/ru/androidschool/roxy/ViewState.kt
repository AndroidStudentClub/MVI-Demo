package ru.androidschool.roxy

import com.ww.roxie.BaseAction
import com.ww.roxie.BaseState
import ru.androidschool.data.FeedArticle

data class ViewState(
    val items: List<FeedArticle> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
): BaseState

sealed class UserIntentionResult {
    object Loading : UserIntentionResult()
    data class ListLoaded(val items: List<FeedArticle>) : UserIntentionResult()
    object Error: UserIntentionResult()
    object ClearError: UserIntentionResult()
}

sealed class UserIntention : BaseAction {
    object LoadArticles : UserIntention()
    object ErrorShown : UserIntention()
}
