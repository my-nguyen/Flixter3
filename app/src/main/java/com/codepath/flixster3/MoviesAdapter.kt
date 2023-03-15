package com.codepath.flixster3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.flixster3.databinding.ItemMovieBinding

class MoviesAdapter(val movies: List<Movie>, val genreMap: GenreMap, val listener: OnClickListener) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {
    interface OnClickListener {
        fun onItemClick(position: Int)
    }

    inner class ViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.apply {
                Glide.with(itemView).load(movie.getBackdrop()).into(backdrop)
                title.text = movie.title
                genres.text = movie.genre_ids.joinToString(", ") { genreMap.map[it]!! }
                voteAverage.text = movie.vote_average.toString()
            }
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val holder = ItemMovieBinding.inflate(inflater, parent, false)
        return ViewHolder(holder)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount() = movies.size
}