package com.sen.synk.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sen.synk.data.dao.AccountDao
import com.sen.synk.data.db.AppDatabase
import com.sen.synk.data.model.Account
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class SynkDbTest {

    private lateinit var accountDao: AccountDao
    private lateinit var db: AppDatabase
    private lateinit var account: Account

    @Before
    fun createDb() {

        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        accountDao = db.accountDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {

        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAccount() {

        account = Account(
            id = 200,
            email = "Abdul Jarvis",
            password = "asdDSA@123",
            username = "jarvis",
            role = "Admin"
        )
        accountDao.upsertAccount(account)
        val result = account.username?.let { accountDao.getAccountByUsername(it) }
        assertThat(result, equalTo(account))
    }

    @Test
    @Throws(Exception::class)
    fun deleteAccount() {

        insertAccount()

        account.username?.let { accountDao.deleteAccountByUsername(it) }
        val result = account.username?.let { accountDao.getAccountByUsername(it) }

        assertThat(result, equalTo(null))
    }
}