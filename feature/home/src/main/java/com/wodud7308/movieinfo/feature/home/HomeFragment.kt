package com.wodud7308.movieinfo.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.wodud7308.movieinfo.core.domain.common.PopularContentType
import com.wodud7308.movieinfo.core.domain.common.TrendingContentType
import com.wodud7308.movieinfo.core.domain.model.Content
import com.wodud7308.movieinfo.core.navigation.DeepLink
import com.wodud7308.movieinfo.core.navigation.navigateToDeepLink
import com.wodud7308.movieinfo.core.ui.common.BaseFragment
import com.wodud7308.movieinfo.core.ui.common.ItemClickListener
import com.wodud7308.movieinfo.feature.home.databinding.FragmentHomeBinding
import com.wodud7308.movieinfo.feature.home.list.HomeContentsListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val viewModel: HomeViewModel by viewModels()
    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, attachToParent)

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
                        contentClickListener
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
                    viewModel.setPopularContentType(item)
                }
            }
        }
    }

    private val contentClickListener = object : ItemClickListener<Content> {
        override fun onClick(item: Content) {
            val navController = findNavController()
            val deepLink = DeepLink.Detail(requireContext(), item.mediaType.toString(), item.id)

            navController.navigateToDeepLink(deepLink)
        }
    }
}
