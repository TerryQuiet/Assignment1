package tk.quietdev.level1.utils

import android.content.Context
import android.content.SharedPreferences


object PrefsHelper {

    private lateinit var preferences: SharedPreferences


    private const val PREFS_NAME = "params"
    private const val USER_ID = "USERID"
    private const val IS_REMEMBER = "isSaveChecked"

    // init from App
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

    fun saveCurrentUserID(userId: String) {
        preferences.edit()
            .putString(USER_ID, userId)
            .apply()
    }

    fun saveString(key: String, userId: String) {
        preferences.edit()
            .putString(key, userId)
            .apply()
    }

    fun getCurrentUser(): String {
        return preferences.getString(USER_ID, "")!!
    }

    fun getPreferences(): SharedPreferences = preferences


}