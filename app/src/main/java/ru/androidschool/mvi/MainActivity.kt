package ru.androidschool.mvi

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch
import ru.androidschool.mvi.feed.FeedIntent
import ru.androidschool.mvi.feed.FeedViewState

class MainActivity : AppCompatActivity() {

    private val feedViewModel: FeedViewModel by viewModels()

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private fun intents(): Flow<FeedIntent> = merge(
        initialIntent(),
        refreshIntent()
    )

    private fun initialIntent(): Flow<FeedIntent> = flowOf(FeedIntent.Initial)

    private fun refreshIntent(): Flow<FeedIntent> = callbackFlow {
        swipeRefreshLayout.setOnRefreshListener {
            trySend(FeedIntent.Refresh())
        }

        awaitClose()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                intents().collect(feedViewModel::processIntent)
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                feedViewModel.viewStates().collect(::render)
            }
        }
    }

    private fun render(state: FeedViewState) {
        swipeRefreshLayout.isRefreshing = state.isLoading
    }
}

