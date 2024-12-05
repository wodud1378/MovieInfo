package com.wodud7308.movieinfo.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.wodud7308.movieinfo.core.domain.common.PopularContentType
import com.wodud7308.movieinfo.core.domain.common.TrendingContentType
import com.wodud7308.movieinfo.core.ui.common.ItemClickListener
import com.wodud7308.movieinfo.feature.home.databinding.FragmentHomeBinding
import com.wodud7308.movieinfo.feature.home.list.HomeContentsListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    @Suppress("ktlint:standard:backing-property-naming")
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

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
            HomeUiState.Loading,
            HomeUiState.Error -> Unit

            is HomeUiState.Success -> {
                binding.contentsList.adapter =
                    HomeContentsListAdapter(requireContext(), state.uiModel, onClickTab)
            }
        }
    }

    private val onClickTab = object : ItemClickListener<Enum<*>> {
        override fun onClick(item: Enum<*>) {
            when (item) {
                is TrendingContentType -> {
                    viewModel.changeTrendingTab(item)
                }

                is PopularContentType -> {
                    viewModel.changePopularTab(item)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
