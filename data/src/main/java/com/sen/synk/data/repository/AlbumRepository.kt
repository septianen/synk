package com.sen.synk.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sen.synk.data.api.ApiService
import com.sen.synk.data.model.Album
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class AlbumRepository(private val apiService: ApiService) {

    fun getMovies(_loginState: MutableStateFlow<Boolean>): Flow<PagingData<Album>> = Pager(
        config = PagingConfig(pageSize = 10, maxSize = 200),
        pagingSourceFactory = { AlbumPagingSource(apiService, _loginState) }
    ).flow
}