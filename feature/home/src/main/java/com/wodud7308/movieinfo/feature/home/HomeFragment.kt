package com.wodud7308.movieinfo.feature.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.wodud7308.movieinfo.core.domain.model.MovieType
import com.wodud7308.movieinfo.feature.home.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    @Suppress("ktlint:standard:backing-property-naming")
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private val adapter: HomeMovieListAdapter = HomeMovieListAdapter()

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

        binding.movies.layoutManager = GridLayoutManager(context, 2)
        binding.movies.adapter = adapter
        binding.movieTypes.setOnCheckedStateChangeListener { group, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                val id = checkedIds.first()
                val type = when (id) {
                    R.id.select_now_playing -> MovieType.NowPlaying
                    R.id.select_upcoming -> MovieType.Upcoming
                    R.id.select_popular -> MovieType.Popular
                    R.id.select_top_rated -> MovieType.TopRated
                    else -> {
                        return@setOnCheckedStateChangeListener
                    }
                }

                viewModel.selectMovieListType(type)
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { state ->
                    when (state) {
                        HomeUiState.Loading,
                        HomeUiState.Error -> Unit

                        is HomeUiState.Success -> {
                            adapter.submitData(state.movies)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
