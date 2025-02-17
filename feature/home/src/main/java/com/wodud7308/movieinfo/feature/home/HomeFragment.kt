package com.wodud7308.movieinfo.feature.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.wodud7308.movieinfo.core.domain.common.PopularContentType
import com.wodud7308.movieinfo.core.domain.common.TrendingContentType
import com.wodud7308.movieinfo.core.navigation.DeepLink
import com.wodud7308.movieinfo.core.navigation.navigateToDeepLink
import com.wodud7308.movieinfo.core.ui.common.BaseFragment
import com.wodud7308.movieinfo.core.ui.common.ItemClickListener
import com.wodud7308.movieinfo.core.ui.content.holder.ContentUiEventListener
import com.wodud7308.movieinfo.core.ui.model.ContentUiModel
import com.wodud7308.movieinfo.feature.home.databinding.FragmentHomeBinding
import com.wodud7308.movieinfo.feature.home.list.HomeContentsListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {
    override val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest {
                    onUiState(it)
                }
            }
        }
    }

    private fun onUiState(state: HomeUiState) {
        when (state) {
            HomeUiState.Loading, // TODO shimmer 적용
            HomeUiState.Error -> Unit // TODO bad_result 적용

            is HomeUiState.ShowData -> {
                binding.contentsList.adapter =
                    HomeContentsListAdapter(
                        requireContext(),
                        state.uiModel,
                        tabClickListener,
                        contentEventListener
                    )
            }
        }
    }

    private val tabClickListener = object : ItemClickListener<Enum<*>> {
        override fun onClick(item: Enum<*>) {
            when (item) {
                is TrendingContentType -> {
                    viewModel.setTrendingContentType(item)
                }

                is PopularContentType -> {
                    viewModel.setPopularContentType(item.mediaType)
                }
            }
        }
    }

    private val contentEventListener = object : ContentUiEventListener {
        override val onClick: ItemClickListener<ContentUiModel> =
            object : ItemClickListener<ContentUiModel> {
                override fun onClick(item: ContentUiModel) {
                    with(item.content) {
                        val navController = findNavController()
                        val deepLink = DeepLink.Detail(requireContext(), mediaType.toString(), id)

                        navController.navigateToDeepLink(deepLink)
                    }
                }
            }

        override val onClickFavorite: ItemClickListener<ContentUiModel>? = null
    }
}
