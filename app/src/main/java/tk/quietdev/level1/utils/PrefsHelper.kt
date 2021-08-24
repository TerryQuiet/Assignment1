package tk.quietdev.level1.utils

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrefsHelper @Inject constructor(@ApplicationContext androidContext: Context) {
    private  val PREFS_NAME = "params"
     val USER_ID = "USERID"
     val IS_REMEMBER = "isSaveChecked"

    private var preferences: SharedPreferences =
        androidContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

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