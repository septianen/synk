package com.sen.synk.domain.usecase

import com.sen.synk.data.constant.LoginStatus
import com.sen.synk.data.constant.TextStatus
import com.sen.synk.data.dao.AccountDao
import com.sen.synk.data.model.Account
import com.sen.synk.domain.utils.TextUtils

class AccountUseCase (
    private val accounDao: AccountDao
) {

    fun login(username: String?, password: String?): LoginStatus? {

        val usernameStatus = TextUtils.validateUsername(username)
        val passwordStatus = TextUtils.validatePassword(password)

        if (usernameStatus == TextStatus.NULL || passwordStatus == TextStatus.NULL)
            return LoginStatus.NULL

        if (findAccount(username) == null)
            return LoginStatus.USER_NOT_FOUND

        if (accounDao.login(username, password) == null)
            return LoginStatus.FAILED

        return LoginStatus.SUCCESS
    }

    fun signup(account: Account): LoginStatus {

        val usernameStatus = TextUtils.validateUsername(account.username)

        val passwordStatus = TextUtils.validatePassword(account.password)

        if (usernameStatus == TextStatus.NULL)
            return LoginStatus.NULL


//        if (passwordStatus == TextStatus.PASSWORD_INVALID || passwordStatus == TextStatus.PASSWORD_LESS_THAN_EIGHT)
//            return LoginStatus.FAILED

        if (findAccount(username = account.username) != null)
            return LoginStatus.FAILED

        addAccount(account)
        return LoginStatus.SUCCESS
    }

    fun addAccount(account: Account) =
        accounDao.insertAccount(account)

    fun findAccount(username: String?): Account? {

        return username?.let { accounDao.getAccountByUsername(it) }
    }

    fun getAccounts(): List<Account>? {
        return accounDao.getAllAccount()
    }
}