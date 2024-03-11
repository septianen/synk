package com.sen.synk.domain.utils

import com.sen.synk.data.constant.PasswordStatus
import com.sen.synk.data.constant.TextStatus
import org.junit.Assert.*
import org.junit.Test

class TextUtilsTest {

    @Test
    fun `insert null email`() {
        val result = TextUtils.validateUsername(null)
        assertEquals(
            TextStatus.NULL,
            result
        )
    }

    @Test
    fun `insert good email`() {
        val email = "asd@asd.com"
        val result = TextUtils.validateUsername(email)
        assertEquals(
            TextStatus.SUCCESS,
            result
        )
    }

    @Test
    fun `insert null password`() {
        val result = TextUtils.validatePassword(null)
        assertEquals(
            PasswordStatus.NULL,
            result
        )
    }

    @Test
    fun `insert password char only`() {
        val password = "kuyakabaisds"
        val result = TextUtils.validatePassword(password)
        assertEquals(
            PasswordStatus.PASSWORD_INVALID,
            result
        )
    }

    @Test
    fun `insert password number only`() {
        val password = "1234567891"
        val result = TextUtils.validatePassword(password)
        assertEquals(
            PasswordStatus.PASSWORD_INVALID,
            result
        )
    }

    @Test
    fun `insert password symbol only`() {
        val password = "@!#@$%^&*$@#"
        val result = TextUtils.validatePassword(password)
        assertEquals(
            PasswordStatus.PASSWORD_INVALID,
            result
        )
    }

    @Test
    fun `insert password less than 8`() {
        val password = "as@312"
        val result = TextUtils.validatePassword(password)
        assertEquals(
            PasswordStatus.PASSWORD_LESS_THAN_EIGHT,
            result
        )
    }

    @Test
    fun `insert good password`() {
        val password = "asdDSA@312319"
        val result = TextUtils.validatePassword(password)
        assertEquals(
            PasswordStatus.SUCCESS,
            result
        )
    }
}