package tk.quietdev.level1.data.datasource.auth

import tk.quietdev.level1.common.Resource
import tk.quietdev.level1.utils.PrefsHelper

class AuthTokenDataSourceSharedPrefs(
    private val prefsHelper: PrefsHelper
) : AuthTokenDataSource {
    override fun saveToken(string: String) {
        prefsHelper.saveString(prefsHelper.TOKEN, string)
    }

    override fun getToken(): String {
        return prefsHelper.getString(prefsHelper.TOKEN) ?: "temp" // TODO: 10/22/2021
    }

    override fun getUserId(): Resource<Int> {
        val id = prefsHelper.getIntOrNull(prefsHelper.CURRENT_USER_ID)
        return if (id != null) Resource.Success(id) else Resource.Error("User id not found")
    }

    override fun putUserId(id: Int) {
        prefsHelper.saveInt(prefsHelper.CURRENT_USER_ID, id)
    }
}