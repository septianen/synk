package com.sen.synk.data.constant

object Constant {

    const val BASE_URL = "https://jsonplaceholder.typicode.com"
    const val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
}

object ARGS {
    const val ACCOUNT = "account"
}

object Message {
    const val EMPTY_USERNAME = "username harus diisi"
    const val EMPTY_PASSWORD = "password harus diisi"
    const val EMPTY_EMAIL = "email harus diisi"
    const val INVALID_EMAIL = "format email tidak sesuai"
    const val USERNAME_ALREADY_EXIST = "username sudah terdaftar"
    const val USERNAME_NOT_FOUND = "username belum terdaftar"
    const val PASSWORD_LESS_THAN_8 = "password minimal 8 karakter"
    const val INVALID_PASSWORD = "password harus terdiri dari huruf besar, kecil, angka, dan simbol"
    const val INVALID_USERNAME_PASSWORD = "username atau password salah"
    const val FALSE_PASSWORD = "password salah"
}