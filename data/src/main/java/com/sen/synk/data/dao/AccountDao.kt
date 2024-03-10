package com.sen.synk.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sen.synk.data.constant.DB
import com.sen.synk.data.model.Account

@Dao
interface AccountDao {

    @Insert
    fun insertAccount(account: Account)

    @Query("SELECT * FROM ${DB.Table.ACCOUNT} WHERE username = :username AND password = :password LIMIT 1")
    fun login(username: String?, password: String?): Account?

    @Query("SELECT * FROM ${DB.Table.ACCOUNT} WHERE username = :username LIMIT 1")
    fun getAccountByUsername(username: String): Account?

    @Query("DELETE FROM ${DB.Table.ACCOUNT} WHERE username = :username")
    fun deleteAccountByUsername(username: String)
}