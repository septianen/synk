package com.sen.synk.viewmodel.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sen.synk.data.constant.LoginStatus
import com.sen.synk.data.model.Account
import com.sen.synk.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (
    val loginUseCase: LoginUseCase,
    app: Application
): AndroidViewModel(app) {

    val accountLiveData = MutableLiveData<Account?>()

    init {
//        insert()
//        login("username")
    }

    private fun login(username: String) = viewModelScope.launch(Dispatchers.IO) {
        val acc = loginUseCase.login(username, "asdDSA@123")
//        accountLiveData.postValue(acc)
    }

    private fun insert() = viewModelScope.launch(Dispatchers.IO) {
        val acc = Account(
        null,
        "namanya",
        "asdDSA@123",
            "asdDSA@123",
        "username",
        2
        )

        when(loginUseCase.signup(acc)) {
            LoginStatus.SUCCESS -> accountLiveData.postValue(acc)
            else -> accountLiveData.postValue(null)
        }
    }
}