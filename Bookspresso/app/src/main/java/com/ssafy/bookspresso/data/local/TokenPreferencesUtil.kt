package com.ssafy.bookspresso.data.local

import android.content.Context
import android.content.SharedPreferences

class TokenPreferencesUtil(context: Context) {

    companion object {
        private const val PREF_NAME = "token_prefs"
        private const val TOKEN_KEY = "Authorization"
    }

    private val preferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        preferences.edit().putString(TOKEN_KEY, token).apply()
    }

    fun getToken(): String? {
        return preferences.getString(TOKEN_KEY, null)
    }

    fun clearToken() {
        preferences.edit().remove(TOKEN_KEY).apply()
    }
}