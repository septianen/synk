package com.sen.synk.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sen.synk.data.api.ApiService
import com.sen.synk.data.model.Album
import kotlinx.coroutines.flow.MutableStateFlow

class AlbumPagingSource (
    private val apiService: ApiService,
    var _loginState: MutableStateFlow<Boolean>
): PagingSource<Int, Album>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Album> {

        return try {

            _loginState.value = true

            val page = params.key ?: 1
            val response = apiService.fetchAlbum(page)
            val nextKey = if (response.isNotEmpty()) page + 1 else null

            _loginState.value = false

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