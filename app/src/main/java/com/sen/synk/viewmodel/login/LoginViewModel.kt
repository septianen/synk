package com.sen.synk.viewmodel.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sen.synk.data.model.Album
import com.sen.synk.data.repository.AlbumRepository
import com.sen.synk.data.RetrofitInstance
import com.sen.synk.data.api.ApiService
import com.sen.synk.data.constant.LoginStatus
import com.sen.synk.data.model.Account
import com.sen.synk.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor (
    val loginUseCase: LoginUseCase,
    app: Application
): AndroidViewModel(app) {

    private val _loginLiveData = MutableLiveData<LoginStatus?>()
    val loginLiveData: LiveData<LoginStatus?>
        get() = _loginLiveData

    fun resetLiveData() {
//        _loginLiveData.postValue(null)
    }

    fun login(account: Account) = viewModelScope.launch(Dispatchers.IO) {
        val login = loginUseCase.login(account.username, account.password)
        _loginLiveData.postValue(login)
    }

    fun signup(account: Account) = viewModelScope.launch(Dispatchers.IO) {
        val login = loginUseCase.signup(account)
        _loginLiveData.postValue(login)
    }
}