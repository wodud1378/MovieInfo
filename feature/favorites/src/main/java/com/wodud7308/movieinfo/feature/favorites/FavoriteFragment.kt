package com.wodud7308.movieinfo.feature.favorites

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.wodud7308.movieinfo.core.navigation.DeepLink
import com.wodud7308.movieinfo.core.navigation.navigateToDeepLink
import com.wodud7308.movieinfo.core.ui.common.BaseFragment
import com.wodud7308.movieinfo.core.ui.common.ItemClickListener
import com.wodud7308.movieinfo.core.ui.content.adapter.ContentListAdapter
import com.wodud7308.movieinfo.core.ui.content.holder.ContentUiEventListener
import com.wodud7308.movieinfo.core.ui.content.state.ContentListState
import com.wodud7308.movieinfo.core.ui.layout.EnumTabLayout
import com.wodud7308.movieinfo.core.ui.model.ContentUiModel
import com.wodud7308.movieinfo.core.ui.util.getString
import com.wodud7308.movieinfo.feature.favorites.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FavoriteViewModel, FragmentFavoriteBinding>(
    FragmentFavoriteBinding::inflate
) {
    override val viewModel: FavoriteViewModel by viewModels()
    private lateinit var adapter: ContentListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initRecyclerView()
        initObservers()

        binding.buttonFilter.setOnClickListener {
            showFilterDialog()
        }
    }

    private fun initAdapter() {
        adapter = ContentListAdapter(contentEventListener)
    }

    private fun initRecyclerView() {
        with(binding.content) {
            adapter = this@FavoriteFragment.adapter
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { observeChangeState() }
                launch { observeChangeMediaType() }
            }
        }
    }

    private suspend fun observeChangeState() {
        viewModel.contentListState.collectLatest { state ->
            onContentListState(state)
        }
    }

    private suspend fun observeChangeMediaType() {
        viewModel.mediaTypeFlow.collectLatest { type ->
            with(binding.buttonFilter) {
                text = type?.getString(context) ?: context.getString(R.string.filter)
            }
        }
    }

    private fun onContentListState(state: ContentListState) {
        when (state) {
            ContentListState.Loading,
            ContentListState.Error -> Unit

            is ContentListState.Success -> {
                adapter.submitList(state.contents)
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

        override val onClickFavorite: ItemClickListener<ContentUiModel> =
            object : ItemClickListener<ContentUiModel> {
                override fun onClick(item: ContentUiModel) =
                    viewModel.toggleFavorite(item)
            }
    }

    private fun showFilterDialog() {
        val selected = viewModel.mediaTypeFlow.value.toFilter()
        val dialog = FilterBottomSheetDialog(requireActivity(), selected).apply {
            setItemClickListener(object : ItemClickListener<Filter> {
                override fun onClick(item: Filter) {
                    viewModel.setMediaType(item.mediaType)
                }
            })
        }

        dialog.show()
    }
}
