package com.sen.synk.viewmodel.edituser

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.sen.synk.domain.usecase.AccountUseCase
import javax.inject.Inject

class EditUserViewModel @Inject constructor(
    val accountUseCase: AccountUseCase,
    application: Application
): AndroidViewModel(application) {
}