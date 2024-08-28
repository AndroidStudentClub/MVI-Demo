package ru.androidschool.roxy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.androidschool.data.FeedArticle
import ru.androidschool.di.getInjector
import ru.androidschool.mvi.databinding.FragmentRoxieBinding


class RoxieFragment : Fragment() {
    private var _binding: FragmentRoxieBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RoxieViewModel by viewModels(
        factoryProducer = { getInjector().provideViewModelFactory() }
    )

    private val adapter = ArticlesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoxieBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.uiRecyclerView.adapter = adapter
        binding.uiRecyclerView.layoutManager = LinearLayoutManager(context)

        binding.uiSwipeRefreshLayout.setOnRefreshListener {
            viewModel.dispatch(UserIntention.LoadArticles)
        }

        viewModel.observableState.observe(viewLifecycleOwner) { state ->
            renderState(state)
        }

        viewModel.dispatch(UserIntention.LoadArticles)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderState(state: ViewState) {
        with(state) {
            when {
                isLoading -> renderLoadingState()
                isError -> renderErrorState()
                else -> renderArticlesState(items)
            }
        }
    }

    private fun renderLoadingState() {
        hideAll()
        binding.uiProgressBar.visibility = View.VISIBLE
    }

    private fun renderErrorState() {
        binding.uiProgressBar.visibility = View.GONE
        Toast.makeText(
            requireContext(),
            "Error wile loading data",
            Toast.LENGTH_SHORT
        ).show()
        viewModel.dispatch(UserIntention.ErrorShown)
    }

    private fun renderArticlesState(items: List<FeedArticle>) {
        hideAll()
        adapter.submitList(items)
        binding.uiRecyclerView.visibility = View.VISIBLE
    }

    private fun hideAll() {
        binding.uiRecyclerView.visibility = View.GONE
        binding.uiProgressBar.visibility = View.GONE
        binding.uiMessage.visibility = View.GONE
        binding.uiSwipeRefreshLayout.isRefreshing = false
    }
}