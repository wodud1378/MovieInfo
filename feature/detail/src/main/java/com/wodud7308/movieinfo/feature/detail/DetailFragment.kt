package com.wodud7308.movieinfo.feature.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.wodud7308.movieinfo.core.domain.model.Content
import com.wodud7308.movieinfo.core.ui.cast.CastListAdapter
import com.wodud7308.movieinfo.core.ui.common.BackdropSize
import com.wodud7308.movieinfo.core.ui.common.BaseFragment
import com.wodud7308.movieinfo.core.ui.common.ImageLoadStateListener
import com.wodud7308.movieinfo.core.ui.common.ImagePath
import com.wodud7308.movieinfo.core.ui.common.PosterSize
import com.wodud7308.movieinfo.core.ui.deco.GridSpacingItemDecoration
import com.wodud7308.movieinfo.core.ui.util.fromUrl
import com.wodud7308.movieinfo.feature.detail.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(
    FragmentDetailBinding::inflate
) {
    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setData(args.mediaType, args.id)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest {
                    onUiState(it)
                }
            }
        }
    }

    private fun onUiState(state: DetailUiState) {
        when (state) {

            DetailUiState.Loading, // TODO shimmer 적용
            DetailUiState.Error -> Unit // TODO bad_result 적용

            is DetailUiState.ShowContent -> {
                updateUi(state.content)
            }
        }
    }

    private fun updateUi(content: Content) {
        with(binding) {
            poster.fromUrl(
                ImagePath.urlOf(content.posterPath, PosterSize.W185),
                posterLoadStateListener
            )
            title.text = content.title
            releaseDate.text = content.releaseDate

            content.detail?.let { detail ->
                backdrop.fromUrl(
                    ImagePath.urlOf(detail.backdropPath, BackdropSize.W300),
                    backdropLoadStateListener
                )
                overview.text = detail.overview
                originalTitle.text = detail.originalTitle
                status.text = detail.status
                genre.text = detail.genres.joinToString(", ") { it.name }
                detail.credits?.let { credit ->
                    val adapter = CastListAdapter()
                    castList.adapter = adapter
                    castList.addItemDecoration(
                        GridSpacingItemDecoration(requireContext())
                    )

                    adapter.submitList(credit.casts)
                }
            }
        }
    }

    private val posterLoadStateListener = object : ImageLoadStateListener {
        override fun onLoadState(isSuccess: Boolean) {
            with(binding) {
                if (isSuccess) {
                    poster.visibility = View.VISIBLE
                    posterError.visibility = View.INVISIBLE
                } else {
                    poster.visibility = View.INVISIBLE
                    posterError.visibility = View.VISIBLE
                }
            }
        }
    }

    private val backdropLoadStateListener = object : ImageLoadStateListener {
        override fun onLoadState(isSuccess: Boolean) {
            if (!isSuccess) {
                binding.backdrop.setImageResource(R.color.backdrop_dim)
            }
        }
    }
}
