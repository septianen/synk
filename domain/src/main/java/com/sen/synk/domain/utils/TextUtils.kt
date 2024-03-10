package com.sen.synk.domain.utils

import com.sen.synk.data.constant.Constant
import com.sen.synk.data.constant.TextStatus
import java.util.regex.Matcher
import java.util.regex.Pattern


object TextUtils {

    fun validateUsername(username: String?): TextStatus {

        return if (username.isNullOrEmpty())
            TextStatus.NULL
        else
            TextStatus.SUCCESS
    }

    fun validatePassword(password: String?): TextStatus {

        return if (password.isNullOrEmpty())
            TextStatus.NULL
        else if (password.length < 8)
            TextStatus.PASSWORD_INVALID
        else if (!isValidPassword(password))
            TextStatus.PASSWORD_INVALID
        else
            TextStatus.SUCCESS
    }

    private fun isValidPassword(password: String): Boolean {
        val pattern: Pattern = Pattern.compile(Constant.PASSWORD_PATTERN)
        val matcher: Matcher = pattern.matcher(password)
        return matcher.matches()
    }
}