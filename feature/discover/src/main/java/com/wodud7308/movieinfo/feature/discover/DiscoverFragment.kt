package com.wodud7308.movieinfo.feature.discover

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.wodud7308.movieinfo.core.domain.common.ContentType
import com.wodud7308.movieinfo.core.domain.common.MediaType
import com.wodud7308.movieinfo.core.navigation.DeepLink
import com.wodud7308.movieinfo.core.navigation.navigateToDeepLink
import com.wodud7308.movieinfo.core.ui.common.BaseFragment
import com.wodud7308.movieinfo.core.ui.common.ItemClickListener
import com.wodud7308.movieinfo.core.ui.content.adapter.PagingContentListAdapter
import com.wodud7308.movieinfo.core.ui.content.holder.ContentUiEventListener
import com.wodud7308.movieinfo.core.ui.layout.EnumTabLayout
import com.wodud7308.movieinfo.core.ui.model.ContentUiModel
import com.wodud7308.movieinfo.core.ui.util.getString
import com.wodud7308.movieinfo.feature.discover.databinding.FragmentDiscoverBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DiscoverFragment : BaseFragment<DiscoverViewModel, FragmentDiscoverBinding>(
    FragmentDiscoverBinding::inflate
) {
    override val viewModel: DiscoverViewModel by viewModels()
    private lateinit var adapter: PagingContentListAdapter

    private var mediaTab: EnumTabLayout<MediaType>? = null
    private var contentTab: EnumTabLayout<ContentType>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initMediaTab()
        initObservers()

        binding.badResult.retry.setOnClickListener {
            adapter.retry()
        }
    }

    private fun initRecyclerView() {
        adapter = PagingContentListAdapter(object : ContentUiEventListener {
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
        
        with(binding.content) {
            adapter = this@DiscoverFragment.adapter
        }
    }

    private fun initMediaTab() {
        mediaTab = EnumTabLayout(
            tabLayout = binding.mediaTab,
            context = requireContext(),
            entries = MediaType.entries,
            getTabString = MediaType::getString
        ) { mediaType ->
            viewModel.setMediaType(mediaType)
            contentTab?.let { tabRoot ->
                val contentType = when (mediaType) {
                    MediaType.Movie -> ContentType.NowPlaying
                    MediaType.Tv -> ContentType.Popular
                }

                tabRoot.selectTab(contentType)
            }
        }

        viewModel.setMediaType(MediaType.Movie)
    }

    private fun initContentTab(
        entries: Iterable<ContentType>
    ) {
        contentTab?.clear()
        contentTab = EnumTabLayout(
            tabLayout = binding.contentTab,
            context = requireContext(),
            entries = entries,
            getTabString = ContentType::getString
        ) { type ->
            viewModel.setContentType(type)
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { observeChangeMediaType() }
                launch { observeChangeContents() }
                launch { observeLoadState() }
                launch { observeChangeFavorites() }
            }
        }
    }

    private suspend fun observeChangeMediaType() {
        viewModel.mediaTypeFlow.collectLatest { data ->
            val entries = when (data) {
                MediaType.Movie -> contentTypesForMovie
                MediaType.Tv -> contentTypesForTv
                else -> null
            }

            entries?.let {
                initContentTab(it)
            }
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
                    progress = View.VISIBLE,
                    content = View.GONE,
                    badResult = View.GONE
                )

                binding.progress.isIndeterminate = true
            }

            is LoadState.Error -> {
                setUiVisibility(
                    progress = View.GONE,
                    content = View.GONE,
                    badResult = View.VISIBLE
                )
            }

            else -> {
                setUiVisibility(
                    progress = View.INVISIBLE,
                    content = View.VISIBLE,
                    badResult = View.GONE
                )
            }
        }
    }

    private fun setUiVisibility(
        progress: Int,
        content: Int,
        badResult: Int
    ) {
        binding.progress.visibility = progress
        binding.content.visibility = content
        binding.badResult.root.visibility = badResult
    }

    override fun onDestroyView() {
        super.onDestroyView()

        mediaTab?.clear()
        contentTab?.clear()

        mediaTab = null
        contentTab = null
    }

    companion object {
        private val contentTypesForMovie: Iterable<ContentType> = ContentType.entries
        private val contentTypesForTv: Iterable<ContentType> =
            listOf(
                ContentType.Popular,
                ContentType.TopRated
            )
    }
}
