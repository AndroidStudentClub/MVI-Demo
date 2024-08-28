package ru.androidschool.roxy

import com.ww.roxie.BaseViewModel
import com.ww.roxie.Reducer
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.androidschool.domain.roxie.ArticlesRepository


class RoxieViewModel(
    private val repository: ArticlesRepository,
) : BaseViewModel<UserIntention, ViewState>() {

    override val initialState = ViewState()

    private val reducer: Reducer<ViewState, UserIntentionResult> = { state, change ->
        when (change) {
            is UserIntentionResult.Loading -> state.copy(isLoading = true)
            is UserIntentionResult.ListLoaded -> state.copy(
                isLoading = false,
                items = change.items,
            )

            is UserIntentionResult.Error -> state.copy(
                isLoading = false,
                isError = true,
            )

            is UserIntentionResult.ClearError -> state.copy(isError = false)
        }
    }

    init {
        bindActions()
    }

    private fun bindActions() {
        val itemsLoadedUserIntentionResult = actions.ofType(UserIntention.LoadArticles::class.java)
            .switchMap {
                repository.getAllArticlesSingle()
                    .subscribeOn(Schedulers.io())
                    .toObservable()
                    .map<UserIntentionResult> { UserIntentionResult.ListLoaded(it) }
                    .defaultIfEmpty(UserIntentionResult.ListLoaded(emptyList()))
                    .onErrorReturn { UserIntentionResult.Error }
                    .startWith(UserIntentionResult.Loading)
            }

        val errorShownUserIntentionResult = actions.ofType(UserIntention.ErrorShown::class.java)
            .map { UserIntentionResult.ClearError }

        val allChanges = Observable.merge(
            itemsLoadedUserIntentionResult,
            errorShownUserIntentionResult,
        )

        disposables.add(
            allChanges
                .scan(initialState, reducer)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(state::setValue) { println("Error $it") }
        )
    }
}