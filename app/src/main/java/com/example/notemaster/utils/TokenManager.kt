package com.example.notemaster.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.notemaster.utils.Constants.PREFS_TOKEN_FILE
import com.example.notemaster.utils.Constants.USER_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(@ApplicationContext context: Context) {
    val pref = context.getSharedPreferences(PREFS_TOKEN_FILE, Context.MODE_PRIVATE)


    fun saveToken(token: String) {
        pref.edit().putString(USER_TOKEN, token).apply()
    }

    fun getToken(): String? {
        return pref.getString(USER_TOKEN, null)
    }
}