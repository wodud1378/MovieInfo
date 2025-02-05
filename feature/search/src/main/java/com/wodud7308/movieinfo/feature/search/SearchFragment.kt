package com.wodud7308.movieinfo.feature.search

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.wodud7308.movieinfo.core.domain.common.MediaType
import com.wodud7308.movieinfo.core.navigation.DeepLink
import com.wodud7308.movieinfo.core.navigation.navigateToDeepLink
import com.wodud7308.movieinfo.core.ui.common.BaseFragment
import com.wodud7308.movieinfo.core.ui.common.ItemClickListener
import com.wodud7308.movieinfo.core.ui.content.adapter.SearchPagingContentListAdapter
import com.wodud7308.movieinfo.core.ui.content.holder.ContentUiEventListener
import com.wodud7308.movieinfo.core.ui.layout.EnumTabLayout
import com.wodud7308.movieinfo.core.ui.model.ContentUiModel
import com.wodud7308.movieinfo.core.ui.util.getString
import com.wodud7308.movieinfo.feature.search.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(
    FragmentSearchBinding::inflate
) {
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var adapter: SearchPagingContentListAdapter

    private var tab: EnumTabLayout<MediaType>? = null

    companion object {
        const val SEARCH_DELAY = 1000L
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initTab()
        initObservers()

        binding.badResult.retry.setOnClickListener {
            adapter.retry()
        }
    }

    private fun initAdapter() {
        adapter = SearchPagingContentListAdapter(object : ContentUiEventListener {
            override val onClick: ItemClickListener<ContentUiModel> =
                object : ItemClickListener<ContentUiModel> {
                    override fun onClick(item: ContentUiModel) {
                        with(item.content) {
                            val navController = findNavController()
                            val deepLink =
                                DeepLink.Detail(requireContext(), mediaType.toString(), id)

                            navController.navigateToDeepLink(deepLink)
                        }
                    }
                }
            override val onClickFavorite: ItemClickListener<ContentUiModel> =
                object : ItemClickListener<ContentUiModel> {
                    override fun onClick(item: ContentUiModel) =
                        viewModel.toggleFavorite(item)
                }
        })

        binding.content.adapter = adapter
    }

    private fun initTab() {
        tab = EnumTabLayout(
            binding.mediaTab,
            requireContext(),
            MediaType.entries,
            MediaType::getString

        ) { type ->
            viewModel.setMediaType(type)
        }

        viewModel.setMediaType(MediaType.Movie)
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { observeChangeQuery() }
                launch { observeChangeContents() }
                launch { observeLoadState() }
                launch { observeChangeFavorites() }
            }
        }
    }

    private suspend fun observeChangeQuery() {
        binding.searchView
            .queryChangeAsStateFlow()
            .manualDebounce(SEARCH_DELAY)
            .distinctUntilChanged()
            .collect { query ->
                requestSearch(query, true)
            }
    }

    private suspend fun observeChangeContents() {
        viewModel.pagerFlow.collectLatest { data ->
            adapter.submitData(data)
        }
    }

    private suspend fun observeLoadState() {
        adapter.loadStateFlow.collectLatest { loadState ->
            onDataLoadState(loadState)
        }
    }

    private suspend fun observeChangeFavorites() {
        viewModel.favoriteContentsFlow.collectLatest { data ->
            adapter.updateFavorites(data)
        }
    }

    private fun onDataLoadState(loadState: CombinedLoadStates) {
        when (loadState.refresh) {
            is LoadState.Loading -> {
                setUiVisibility(
                    tab = View.INVISIBLE,
                    progress = View.VISIBLE,
                    scroll = View.GONE,
                    badResult = View.GONE
                )
                binding.progress.isIndeterminate = true
            }

            is LoadState.Error -> {
                setUiVisibility(
                    tab = View.GONE,
                    progress = View.GONE,
                    scroll = View.GONE,
                    badResult = View.VISIBLE
                )
            }

            is LoadState.NotLoading -> {
                if (adapter.itemCount > 0) {
                    setUiVisibility(
                        tab = View.VISIBLE,
                        progress = View.INVISIBLE,
                        scroll = View.VISIBLE,
                        badResult = View.GONE
                    )
                } else {
                    setUiVisibility(
                        tab = View.GONE,
                        progress = View.GONE,
                        scroll = View.GONE,
                        badResult = View.GONE
                    )
                }
            }
        }
    }

    private fun setUiVisibility(tab: Int, progress: Int, scroll: Int, badResult: Int) {
        binding.mediaTab.visibility = tab
        binding.progress.visibility = progress
        binding.badResult.root.visibility = badResult
        binding.content.visibility = scroll
    }

    private fun SearchView.queryChangeAsStateFlow(): StateFlow<String> {
        val query = MutableStateFlow("")

        setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { requestSearch(it) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { query.value = it }
                return false
            }
        })

        return query
    }

    private fun <T> Flow<T>.manualDebounce(ms: Long): Flow<T> = flow {
        var lastEmitTime = 0L

        collect { value ->
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastEmitTime >= ms) {
                emit(value)
                lastEmitTime = currentTime
            }
        }
    }

    private fun requestSearch(query: String, keepFocus: Boolean = false) {
        viewModel.setQuery(query)

        if (!keepFocus) {
            binding.searchView.clearFocus()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        tab?.clear()
        tab = null
    }
}
