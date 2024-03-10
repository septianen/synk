package com.sen.synk.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sen.synk.data.api.ApiService
import com.sen.synk.data.model.Album

class AlbumPagingSource (
    private val apiService: ApiService
): PagingSource<Int, Album>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Album> {

        return try {
            val page = params.key ?: 1
            val response = apiService.fetchAlbum(page)
            val nextKey = if (response.isNotEmpty()) page + 1 else null

            LoadResult.Page(
                data = response,
                prevKey = if (page > 1) page - 1 else null,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Album>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}