package com.sen.synk.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sen.synk.data.constant.DB
import java.io.Serializable

@Entity(tableName = DB.Table.ACCOUNT)
data class Account(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var email: String? = null,
    var password: String? = null,
    var confirmPassword: String? = null,
    var username: String? = null,
    var role: String? = null
): Serializable