package com.sen.synk.viewmodel.edituser

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sen.synk.data.constant.Message
import com.sen.synk.data.constant.Resource
import com.sen.synk.data.constant.SignupStatus
import com.sen.synk.data.model.Account
import com.sen.synk.domain.usecase.AccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditUserViewModel @Inject constructor(
    val accountUseCase: AccountUseCase,
    application: Application
): AndroidViewModel(application) {

    private val _accountLiveData = MutableLiveData<Resource<Account>>()
    val accountLiveData: LiveData<Resource<Account>>
        get() = _accountLiveData

    fun saveData(account: Account) = viewModelScope.launch(Dispatchers.IO) {
        val status = accountUseCase.updateAccount(account = account)
        when(status) {
            SignupStatus.NULL_USERNAME -> _accountLiveData.postValue(Resource.Error(Message.EMPTY_USERNAME))
            SignupStatus.NULL_PASSWORD -> _accountLiveData.postValue(Resource.Error(Message.EMPTY_PASSWORD))
            SignupStatus.USER_ALREADY_EXIST -> _accountLiveData.postValue(Resource.Error(Message.USERNAME_ALREADY_EXIST))
            SignupStatus.PASSWORD_LESS_THAN_8 -> _accountLiveData.postValue(Resource.Error(Message.PASSWORD_LESS_THAN_8))
            SignupStatus.INVALID_PASSWORD -> _accountLiveData.postValue(Resource.Error(Message.INVALID_PASSWORD))
            SignupStatus.NULL_EMAIL -> _accountLiveData.postValue(Resource.Error(Message.EMPTY_EMAIL))
            SignupStatus.INVALID_EMAIL -> _accountLiveData.postValue(Resource.Error(Message.INVALID_EMAIL))
            SignupStatus.SUCCESS -> _accountLiveData.postValue(Resource.Success(account))
        }

    }
}