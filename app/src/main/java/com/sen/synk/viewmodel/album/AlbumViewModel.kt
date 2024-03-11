package com.sen.synk.viewmodel.album

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sen.synk.data.model.Album
import com.sen.synk.data.repository.AlbumRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor (
    repository: AlbumRepository,
    app: Application
): AndroidViewModel(app) {

    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> get() = _loadingState.asStateFlow()

    val albumLiveData: LiveData<PagingData<Album>> =
        repository.getMovies(_loadingState)
            .cachedIn(viewModelScope).asLiveData()


}