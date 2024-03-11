package com.sen.synk.data.constant

enum class LoginStatus {
    NULL_USERNAME,
    NULL_PASSWORD,
    SUCCESS,
    FAILED,
    USER_NOT_FOUND
}

enum class SignupStatus {
    NULL_USERNAME,
    NULL_PASSWORD,
    NULL_EMAIL,
    USER_ALREADY_EXIST,
    PASSWORD_LESS_THAN_8,
    INVALID_PASSWORD,
    INVALID_EMAIL,
    SUCCESS
}