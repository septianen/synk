package com.sen.synk.domain.utils

import com.sen.synk.data.constant.Constant
import com.sen.synk.data.constant.PasswordStatus
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

    fun validateEmail(email: String?): TextStatus {

        return if (email.isNullOrEmpty())
            TextStatus.NULL
        else if (!isEmailValid(email))
            TextStatus.INVALID
        else
            TextStatus.SUCCESS
    }

    private fun isEmailValid(email: String): Boolean {
        val pattern: Pattern = Pattern.compile(Constant.EMAIL_PATTERN)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }

    fun validatePassword(password: String?): PasswordStatus {

        return if (password.isNullOrEmpty())
            PasswordStatus.NULL
        else if (password.length < 8)
            PasswordStatus.PASSWORD_LESS_THAN_EIGHT
        else if (!isValidPassword(password))
            PasswordStatus.PASSWORD_INVALID
        else
            PasswordStatus.SUCCESS
    }

    private fun isValidPassword(password: String): Boolean {
        val pattern: Pattern = Pattern.compile(Constant.PASSWORD_PATTERN)
        val matcher: Matcher = pattern.matcher(password)
        return matcher.matches()
    }
}