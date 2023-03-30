package com.skillbox.a017_recyclerview.pagedmovielist

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.skillbox.a017_recyclerview.models.Movie

class MoviePagingSource : PagingSource<Int, Movie>() {
    private val repository = MoviePagedListRepository()
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? = FIRST_PAGE


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            repository.getTopList(page)
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else page + 1
                )
            },
            onFailure = { LoadResult.Error(it) }
        )
    }

    private companion object {
        private val FIRST_PAGE = 1
    }
}
