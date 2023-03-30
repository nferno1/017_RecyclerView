package com.skillbox.a017_recyclerview.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skillbox.a017_recyclerview.databinding.MovieItemBinding
import com.skillbox.a017_recyclerview.models.Movie

class MovieAdapter : RecyclerView.Adapter<MovieViewHolder>() {
    private var data: List<Movie> = emptyList()
    fun setData(data: List<Movie>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = data.getOrNull(position)
        with(holder.binding) {
            title.text = item?.nameRu ?: ""
            genres.text = item?.genres?.joinToString(", ") { it.genre }
            description.text = "Премьера ${item?.premiereRu}"
            countries.text = item?.countries?.joinToString(", ") { it.country }
            item?.let {
                Glide
                    .with(poster.context)
                    .load(it.posterUrlPreview)
                    .into(poster)
            }
        }
    }
}

class MovieViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root)
