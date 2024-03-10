package com.sen.synk.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sen.synk.data.constant.DB

@Entity(tableName = DB.Table.ACCOUNT)
data class Account(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var fullName: String? = null,
    var password: String? = null,
    var confirmPassword: String? = null,
    var username: String? = null,
    var userType: Int? = null
)