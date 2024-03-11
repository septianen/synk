package com.sen.synk.viewmodel.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sen.synk.data.constant.Message
import com.sen.synk.data.constant.Resource
import com.sen.synk.data.model.Account
import com.sen.synk.domain.usecase.AccountUseCase
import com.sen.synk.pref.SharedPreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    val accountUseCase: AccountUseCase,
    application: Application
): AndroidViewModel(application) {

    private val username by lazy {
        SharedPreferenceManager.getUsername(application.applicationContext)
    }

    private val _userLiveData = MutableLiveData<Resource<List<Account>?>>()
    val userLiveData: LiveData<Resource<List<Account>?>>
        get() = _userLiveData

    init {
        getAccounts()
    }

    private fun getAccounts() = viewModelScope.launch(Dispatchers.IO) {

        val accounts = accountUseCase.getAccounts(username)
        if (accounts.isNullOrEmpty())
            _userLiveData.postValue(Resource.Success(ArrayList()))
        else
            _userLiveData.postValue(Resource.Success(accounts))
    }

    fun deleteAccount(account: Account, password: String?) = viewModelScope.launch(Dispatchers.IO) {
        if (password.isNullOrEmpty())
            _userLiveData.postValue(Resource.Error(Message.EMPTY_PASSWORD))
        else if (!accountUseCase.isPasswordValid(username!!, password))
            _userLiveData.postValue(Resource.Error(Message.FALSE_PASSWORD))
        else {
            accountUseCase.deleteAccount(account)
            getAccounts()
        }
    }
}