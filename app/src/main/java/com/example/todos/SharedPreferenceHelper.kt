package com.example.todos

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreferenceHelper @Inject constructor(context : Context) {
    private val sharedPreferences: SharedPreferences = context.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    companion object {
        const val PREF_NAME = "MyPrefs"
        const val PREF_KEY_USER_ID = "userId"
        const val PREF_KEY_IS_LOGGED_IN = "isLoggedIn"
        const val SPLASH_SCREEN_SHOWN = "splashScreenShown"
    }

    var userId: Int
        get() = sharedPreferences.getInt(PREF_KEY_USER_ID, -1)
        set(value) = editor.putInt(PREF_KEY_USER_ID, value).apply()

    var isLoggedIn: Boolean
        get() = sharedPreferences.getBoolean(PREF_KEY_IS_LOGGED_IN, false)
        set(value) = editor.putBoolean(PREF_KEY_IS_LOGGED_IN, value).apply()

    var isSplashScreenShown: Boolean
        get() = sharedPreferences.getBoolean(SPLASH_SCREEN_SHOWN, false)
        set(value) = editor.putBoolean(SPLASH_SCREEN_SHOWN, value).apply()

    fun clear() {
        editor.clear().apply()
    }

    fun remove(key: String) {
        editor.remove(key).apply()
    }
}