package com.ssafy.bookspresso.data.local

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.ssafy.bookspresso.data.model.dto.User

private const val TAG = "SharedPreferencesUtil_싸피"

class SharedPreferencesUtil(context: Context) {
    val SHARED_PREFERENCES_NAME = "smartstore_preference"
    val COOKIES_KEY_NAME = "cookies"

    var preferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    //사용자 정보 저장
    fun addUser(user: User) {
        val editor = preferences.edit()
        editor.putString("id", user.id)
        editor.putString("name", user.name)
        editor.apply()
    }

    fun getUser(): User {
        val id = preferences.getString("id", "")
        if (id != "") {
            val name = preferences.getString("name", "")
            return User(id!!, name!!, "", 0, "")
        } else {
            return User()
        }
    }

    fun deleteUser() {
        //preference 지우기
        val editor = preferences.edit()
        editor.remove("id")
        editor.remove("name")
        editor.apply()
    }

    fun addUserCookie(cookies: HashSet<String>) {
        val editor = preferences.edit()
        editor.putStringSet(COOKIES_KEY_NAME, cookies)
        editor.apply()
    }

    fun getUserCookie(): MutableSet<String>? {
        return preferences.getStringSet(COOKIES_KEY_NAME, HashSet())
    }

    fun deleteUserCookie() {
        preferences.edit().remove(COOKIES_KEY_NAME).apply()
    }


    fun addNotice(info: String) {
        val list = getNotice()

        list.add(info)
        val json = Gson().toJson(list)

        preferences.edit().let {
            it.putString("notice", json)
            it.apply()
        }
    }

    fun setNotice(list: MutableList<String>) {
        preferences.edit().let {
            it.putString("notice", Gson().toJson(list))
            it.apply()
        }
    }

    fun getNotice(): MutableList<String> {
        val str = preferences.getString("notice", "")!!
        val list = if (str.isEmpty()) mutableListOf<String>() else Gson().fromJson(
            str,
            MutableList::class.java
        ) as MutableList<String>

        return list
    }

    fun saveLastShownDate(context: Context, key: String, value: String) {
        val prefs = context.getSharedPreferences("beacon_prefs", Context.MODE_PRIVATE)
        prefs.edit().putString(key, value).apply()
    }

    fun getLastShownDate(context: Context, key: String): String? {
        val prefs = context.getSharedPreferences("beacon_prefs", Context.MODE_PRIVATE)
        return prefs.getString(key, null)
    }

    fun getString(key: String): String? {
        return preferences.getString(key, null)
    }

    fun addString(key: String, value: String) {
        val editor = preferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun remove(s: String) {
        val editor = preferences.edit()
        editor.remove(s).apply()

    }

    // SharedPreferencesUtil.kt

    fun saveTable(table: String) {
        Log.d(TAG, "saveTable: $table")
        preferences.edit().putString("table", table).apply()
    }

    fun getTable(): String? {
        val value = preferences.getString("table", null)
        Log.d(TAG, "getTable: $value")
        return value
    }

    fun removeTable() {
        preferences.edit().remove("table").apply()
    }


}