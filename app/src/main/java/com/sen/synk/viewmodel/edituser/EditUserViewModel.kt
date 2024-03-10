package com.sen.synk.viewmodel.edituser

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sen.synk.data.constant.Resource
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
        accountUseCase.upsertAccount(account = account)
        _accountLiveData.postValue(Resource.Success(account))
    }
}