package com.sen.synk.viewmodel.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sen.synk.data.constant.LoginStatus
import com.sen.synk.data.constant.Message
import com.sen.synk.data.constant.Resource
import com.sen.synk.data.constant.SignupStatus
import com.sen.synk.data.model.Account
import com.sen.synk.domain.usecase.AccountUseCase
import com.sen.synk.pref.SharedPreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor (
    val loginUseCase: AccountUseCase,
    app: Application
): AndroidViewModel(app) {

    private val isLoggedIn by lazy {
        SharedPreferenceManager.isLoggedIn(app.applicationContext)
    }

    private val _loginLiveData = MutableLiveData<Resource<Account>>()
    val loginLiveData: LiveData<Resource<Account>>
        get() = _loginLiveData

    init {
        if (isLoggedIn)
            findAccount(
                SharedPreferenceManager.getUsername(app.applicationContext)!!
            )
    }

    fun login(account: Account) = viewModelScope.launch(Dispatchers.IO) {

        val loginStatus = loginUseCase.login(account.username, account.password)

        when(loginStatus) {
            LoginStatus.NULL_USERNAME -> _loginLiveData.postValue(Resource.Error(Message.EMPTY_USERNAME))
            LoginStatus.NULL_PASSWORD -> _loginLiveData.postValue(Resource.Error(Message.EMPTY_PASSWORD))
            LoginStatus.USER_NOT_FOUND -> _loginLiveData.postValue(Resource.Error(Message.USERNAME_NOT_FOUND))
            LoginStatus.FAILED -> _loginLiveData.postValue(Resource.Error(Message.INVALID_USERNAME_PASSWORD))
            LoginStatus.SUCCESS -> account.username?.let { findAccount(it) }
        }
    }

    fun signup(account: Account) = viewModelScope.launch(Dispatchers.IO) {

        val signupStatus = loginUseCase.signup(account)

        when(signupStatus) {
            SignupStatus.NULL_USERNAME -> _loginLiveData.postValue(Resource.Error(Message.EMPTY_USERNAME))
            SignupStatus.NULL_PASSWORD -> _loginLiveData.postValue(Resource.Error(Message.EMPTY_PASSWORD))
            SignupStatus.USER_ALREADY_EXIST -> _loginLiveData.postValue(Resource.Error(Message.USERNAME_ALREADY_EXIST))
            SignupStatus.PASSWORD_LESS_THAN_8 -> _loginLiveData.postValue(Resource.Error(Message.PASSWORD_LESS_THAN_8))
            SignupStatus.INVALID_PASSWORD -> _loginLiveData.postValue(Resource.Error(Message.INVALID_PASSWORD))
            SignupStatus.NULL_EMAIL -> _loginLiveData.postValue(Resource.Error(Message.EMPTY_EMAIL))
            SignupStatus.INVALID_EMAIL -> _loginLiveData.postValue(Resource.Error(Message.INVALID_EMAIL))
            SignupStatus.SUCCESS -> account.username?.let { findAccount(it) }
        }
    }

    private fun findAccount(username: String) = viewModelScope.launch(Dispatchers.IO) {

        val account = loginUseCase.findAccount(username = username)
        _loginLiveData.postValue(
            Resource.Success(
                account ?: Account()
            )
        )
    }
}