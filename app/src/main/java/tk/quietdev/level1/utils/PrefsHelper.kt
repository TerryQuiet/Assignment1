package tk.quietdev.level1.utils

import android.content.Context
import android.content.SharedPreferences


object PrefsHelper {

    private lateinit var preferences: SharedPreferences

    private const val PREFS_NAME = "params"
    const val USER_ID = "USERID"
    const val IS_REMEMBER = "isSaveChecked"

    // init from App
    fun init(context: Context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun clearPreferences() {
        preferences.edit().clear().apply()
    }


    fun getRememberState(): Boolean = preferences.getBoolean(IS_REMEMBER, false)

    fun saveString(key: String, userId: String) {
        preferences.edit()
            .putString(key, userId)
            .apply()
    }

    fun saveInt(key: String, value: Int) {
        preferences.edit()
            .putInt(key, value)
            .apply()
    }

    fun saveBoolean(key: String, value: Boolean) {
        preferences.edit()
            .putBoolean(key, value)
            .apply()
    }

    fun getIntOrNull(key: String): Int? {
        return if (preferences.contains(key)) {
            preferences.getInt(key, -1)
        } else
            null
    }

    fun getPreferences(): SharedPreferences = preferences

}