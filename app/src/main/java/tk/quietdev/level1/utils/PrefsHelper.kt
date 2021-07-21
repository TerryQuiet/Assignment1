package tk.quietdev.level1.utils

import android.content.Context
import android.content.SharedPreferences


object PrefsHelper {

    private lateinit var preferences: SharedPreferences

    private const val PREFS_NAME = "params"
    private const val IS_REMEMBER = "isSaveChecked"

    fun init(context: Context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun clearPreferences() {
        preferences.edit().clear().apply()
    }

    fun saveRememberState(isRemember: Boolean) {
        preferences.edit()
            .putBoolean(IS_REMEMBER, isRemember)
            .apply()
    }

    fun getRememberState(): Boolean = preferences.getBoolean(IS_REMEMBER, false)
}