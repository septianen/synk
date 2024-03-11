package com.sen.synk.pref

import android.content.Context
import android.content.SharedPreferences

object SharedPreferenceManager {

    private const val PREFS_NAME = "MyPrefs"
    private const val KEY_USERNAME = "username"
    private const val KEY_IS_LOGGED_IN = "isLoggedIn"

    fun saveLoginInfo(context: Context, username: String) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        // Simpan informasi login
        editor.putString(KEY_USERNAME, username)
        editor.putBoolean(KEY_IS_LOGGED_IN, true)

        // Terapkan perubahan
        editor.apply()
    }

    fun getUsername(context: Context): String? {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_USERNAME, null)
    }

    fun isLoggedIn(context: Context): Boolean {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun clearLoginInfo(context: Context) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        // Hapus informasi login
        editor.remove(KEY_USERNAME)
        editor.remove(KEY_IS_LOGGED_IN)

        // Terapkan perubahan
        editor.apply()
    }
}
