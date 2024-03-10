package com.sen.synk.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sen.synk.data.dao.AccountDao
import com.sen.synk.data.model.Account

@Database(
    entities = [Account::class], version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
}