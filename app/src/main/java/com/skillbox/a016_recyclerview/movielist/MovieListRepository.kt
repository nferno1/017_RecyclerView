package com.skillbox.a017_recyclerview.movielist

import com.skillbox.a017_recyclerview.api.retrofit
import com.skillbox.a017_recyclerview.models.Movie
import kotlinx.coroutines.delay

class MovieListRepository {
    suspend fun getPremieres(year: Int, month: String): List<Movie> {
        return retrofit.movies(year, month).items
    }
}
