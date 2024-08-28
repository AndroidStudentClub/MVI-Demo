package ru.androidschool.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.androidschool.mvi.feed.FeedIntent
import ru.androidschool.mvi.feed.FeedViewState
import ru.androidschool.mvi.feed.toProcessor
import ru.androidschool.mvi.feed.toViewState

@OptIn(ExperimentalCoroutinesApi::class)
class FeedViewModel : ViewModel() {

    private val _intents: MutableStateFlow<FeedIntent?> = MutableStateFlow(null)
    private val _viewStates: MutableStateFlow<FeedViewState> =
        MutableStateFlow(FeedViewState.initial)

    init {

        _intents
            .filterNotNull()
            .toProcessor()
            .flatMapConcat { it.process() }
            .toViewState()
            .onEach { _viewStates.value = it }
            .launchIn(viewModelScope)
    }

    fun processIntent(intent: FeedIntent) {
        _intents.value = intent
    }

    fun viewStates(): Flow<FeedViewState> = _viewStates
}


