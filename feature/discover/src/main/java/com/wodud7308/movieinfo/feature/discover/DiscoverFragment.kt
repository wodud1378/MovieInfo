package com.wodud7308.movieinfo.feature.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.wodud7308.movieinfo.core.domain.common.ContentType
import com.wodud7308.movieinfo.core.domain.common.MediaType
import com.wodud7308.movieinfo.core.domain.model.Content
import com.wodud7308.movieinfo.core.navigation.DeepLink
import com.wodud7308.movieinfo.core.navigation.navigateToDeepLink
import com.wodud7308.movieinfo.core.ui.common.BaseFragment
import com.wodud7308.movieinfo.core.ui.common.ItemClickListener
import com.wodud7308.movieinfo.core.ui.content.adapter.PagingContentListAdapter
import com.wodud7308.movieinfo.core.ui.deco.GridSpacingItemDecoration
import com.wodud7308.movieinfo.core.ui.layout.EnumTabLayout
import com.wodud7308.movieinfo.core.ui.util.getString
import com.wodud7308.movieinfo.feature.discover.databinding.FragmentDiscoverBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DiscoverFragment : BaseFragment<FragmentDiscoverBinding>() {
    private val viewModel: DiscoverViewModel by viewModels()
    private lateinit var adapter: PagingContentListAdapter

    private var mediaTab: EnumTabLayout<MediaType>? = null
    private var contentTab: EnumTabLayout<ContentType>? = null

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentDiscoverBinding =
        FragmentDiscoverBinding.inflate(inflater, container, attachToParent)

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
        adapter = PagingContentListAdapter(contentClickListener)
        with(binding.content.scrollView) {
            adapter = this@DiscoverFragment.adapter
            addItemDecoration(
                GridSpacingItemDecoration(requireContext(), 32)
            )
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
                launch { observeChangeData() }
                launch { observeLoadState() }
            }
        }
    }

    private suspend fun observeChangeMediaType() {
        viewModel.mediaTypeFlow.collectLatest { data ->
            val entries = when (data) {
                MediaType.Movie -> contentTypesForMovie
                MediaType.Tv -> contentTypesForTv
            }

            initContentTab(entries)
        }
    }

    private suspend fun observeChangeData() {
        viewModel.pagerFlow.collectLatest { data ->
            adapter.submitData(data)
        }
    }

    private suspend fun observeLoadState() {
        adapter.loadStateFlow.collectLatest { loadState ->
            onDataLoadState(loadState)
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
        binding.content.root.visibility = content
        binding.badResult.root.visibility = badResult
    }

    private val contentClickListener = object : ItemClickListener<Content> {
        override fun onClick(item: Content) {
            val navController = findNavController()
            val deepLink = DeepLink.Detail(requireContext(), item.mediaType.toString(), item.id)

            navController.navigateToDeepLink(deepLink)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        mediaTab?.clear()
        contentTab?.clear()
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
