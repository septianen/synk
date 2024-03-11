package com.sen.synk.domain.usecase

import com.sen.synk.data.constant.LoginStatus
import com.sen.synk.data.constant.PasswordStatus
import com.sen.synk.data.constant.Resource
import com.sen.synk.data.constant.SignupStatus
import com.sen.synk.data.constant.TextStatus
import com.sen.synk.data.dao.AccountDao
import com.sen.synk.data.model.Account
import com.sen.synk.domain.utils.TextUtils

class AccountUseCase (
    private val accounDao: AccountDao
) {

    fun login(username: String?, password: String?): LoginStatus {

        val usernameStatus = TextUtils.validateUsername(username)
        val passwordStatus = TextUtils.validatePassword(password)

        if (usernameStatus == TextStatus.NULL)
            return LoginStatus.NULL_USERNAME

        if (passwordStatus == PasswordStatus.NULL)
            return LoginStatus.NULL_PASSWORD

        if (findAccount(username) == null)
            return LoginStatus.USER_NOT_FOUND

        if (accounDao.login(username, password) == null)
            return LoginStatus.FAILED

        return LoginStatus.SUCCESS
    }

    fun signup(account: Account): SignupStatus {

        val usernameStatus = TextUtils.validateUsername(account.username)
        val emailStatus = TextUtils.validateEmail(account.email)
        val passwordStatus = TextUtils.validatePassword(account.password)

        if (usernameStatus == TextStatus.NULL)
            return SignupStatus.NULL_USERNAME

        if (findAccount(username = account.username) != null)
            return SignupStatus.USER_ALREADY_EXIST

        when(passwordStatus) {
            PasswordStatus.NULL -> return SignupStatus.NULL_PASSWORD
            PasswordStatus.PASSWORD_LESS_THAN_EIGHT -> return SignupStatus.PASSWORD_LESS_THAN_8
            PasswordStatus.PASSWORD_INVALID -> return SignupStatus.INVALID_PASSWORD
            else -> {}
        }

        if (emailStatus == TextStatus.NULL)
            return SignupStatus.NULL_EMAIL


        upsertAccount(account)
        return SignupStatus.SUCCESS
    }

    fun upsertAccount(account: Account) =
        accounDao.upsertAccount(account)

    fun findAccount(username: String?): Account? {

        return username?.let { accounDao.getAccountByUsername(it) }
    }

    fun getAccounts(): List<Account>? {
        return accounDao.getAllAccount()
    }
}