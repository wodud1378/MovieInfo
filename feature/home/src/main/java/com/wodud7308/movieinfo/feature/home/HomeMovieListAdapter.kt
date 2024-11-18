package com.wodud7308.movieinfo.feature.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wodud7308.movieinfo.core.domain.model.Movie
import com.wodud7308.movieinfo.core.presentation.common.PosterPath
import com.wodud7308.movieinfo.core.presentation.common.PosterSize
import com.wodud7308.movieinfo.core.presentation.databinding.CardMovieBinding

class HomeMovieListAdapter :
    PagingDataAdapter<Movie, HomeMovieListAdapter.ViewHolder>(DiffCallback) {
    inner class ViewHolder(
        val binding: CardMovieBinding,
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        with(holder.binding) {
            peek(position)?.let { movie ->
                with(poster154) {
                    Glide.with(context).load(PosterPath.urlOf(movie.posterPath, PosterSize.W154))
                        .into(this)
                }

                title.text = movie.title
                releaseDate.text = movie.releaseDate
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        CardMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false).let {
            return ViewHolder(it)
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(
                oldItem: Movie,
                newItem: Movie,
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Movie,
                newItem: Movie,
            ): Boolean = oldItem == newItem
        }
    }
}
