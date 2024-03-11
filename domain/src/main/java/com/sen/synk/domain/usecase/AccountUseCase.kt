package com.sen.synk.domain.usecase

import com.sen.synk.data.constant.LoginStatus
import com.sen.synk.data.constant.PasswordStatus
import com.sen.synk.data.constant.SignupStatus
import com.sen.synk.data.constant.TextStatus
import com.sen.synk.data.dao.AccountDao
import com.sen.synk.data.model.Account
import com.sen.synk.domain.utils.TextUtils

class AccountUseCase (
    private val accountDao: AccountDao
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

        if (accountDao.login(username, password) == null)
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
        accountDao.upsertAccount(account)

    fun deleteAccount(account: Account) =
        accountDao.delete(account)

    fun findAccount(username: String?): Account? {

        return username?.let { accountDao.getAccountByUsername(it) }
    }

    fun getAccounts(username: String?): List<Account>? {
        return accountDao.getAllAccount(username)
    }

    fun isPasswordValid(username: String, password: String): Boolean {

        return if(accountDao.getAccount(username, password) == null)
            false
        else
            true
    }
}