package com.sen.synk.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sen.synk.data.constant.DB
import com.sen.synk.data.model.Account

@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertAccount(account: Account)

    @Query("SELECT * FROM ${DB.Table.ACCOUNT} WHERE username = :username AND password = :password LIMIT 1")
    fun login(username: String?, password: String?): Account?

    @Query("SELECT * FROM ${DB.Table.ACCOUNT} WHERE username != :username")
    fun getAllAccount(username: String?): List<Account>?

    @Query("SELECT * FROM ${DB.Table.ACCOUNT} WHERE username = :username LIMIT 1")
    fun getAccountByUsername(username: String): Account?

    @Query("SELECT * FROM ${DB.Table.ACCOUNT} WHERE username = :username AND password = :password LIMIT 1")
    fun getAccount(username: String, password: String): Account?

    @Query("DELETE FROM ${DB.Table.ACCOUNT} WHERE username = :username")
    fun deleteAccountByUsername(username: String)

    @Delete
    fun delete(account: Account)
}