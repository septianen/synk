package com.sen.synk.viewmodel.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sen.synk.data.constant.LoginStatus
import com.sen.synk.data.model.Account
import com.sen.synk.domain.usecase.AccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    val accountUseCase: AccountUseCase,
    application: Application
): AndroidViewModel(application) {

    private val _userLiveData = MutableLiveData<List<Account>?>()
    val userLiveData: LiveData<List<Account>?>
        get() = _userLiveData

    init {
        getAccounts()
    }

    private fun getAccounts() = viewModelScope.launch(Dispatchers.IO) {

        val accounts = accountUseCase.getAccounts()
        if (accounts.isNullOrEmpty())
            _userLiveData.postValue(ArrayList())
        else
            _userLiveData.postValue(accounts)
    }
}