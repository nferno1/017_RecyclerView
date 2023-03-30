package com.skillbox.a017_recyclerview.pagedmovielist

import com.skillbox.a017_recyclerview.api.retrofit
import com.skillbox.a017_recyclerview.models.Movie
import kotlinx.coroutines.delay

class MoviePagedListRepository {
    suspend fun getTopList(page: Int): List<Movie> {
        delay(2000)
        return retrofit.topList(page).films
    }
}
