package com.sen.synk.viewmodel.album

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sen.synk.data.RetrofitInstance
import com.sen.synk.data.api.ApiService
import com.sen.synk.data.model.Album
import com.sen.synk.data.repository.AlbumRepository

class AlbumViewModel(
    app: Application
): AndroidViewModel(app) {

    val repository by lazy {
        AlbumRepository(
            RetrofitInstance.buildRetrofit(context = app.applicationContext).create(ApiService::class.java)
        )
    }

    val albumLiveData: LiveData<PagingData<Album>> =
        repository.getMovies()
            .cachedIn(viewModelScope)
            .asLiveData()


}