package tk.quietdev.level1.repository

import android.content.Context
import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tk.quietdev.level1.R
import tk.quietdev.level1.api.ShppApi
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.models.shppApi2.AuthResonse
import tk.quietdev.level1.models.shppApi2.AuthUser
import tk.quietdev.level1.models.shppApi2.ErrorRegResponse

class RemoteApiRepository(
    @ApplicationContext private val androidContext: Context,
    private val api: ShppApi,
) : Repository {

    private val moshi by lazy { moshiResponseMapper() }

    override fun updateUser(updatedUserModel: UserModel) {
        TODO("Not yet implemented")
    }

    override fun addUser(userModel: UserModel): UserModel {
        TODO("Not yet implemented")
    }

    override fun getUserWithNoValidation(id: Int): UserModel? {
        TODO("Not yet implemented")
    }

    override fun getUserWithValidation(email: String, password: String): UserModel? {
        TODO("Not yet implemented")
    }

    override fun getUserList(amount: Int): List<UserModel> {
        TODO("Not yet implemented")
    }

    override suspend fun userRegistration(login: String, password: String) {
        try {
            val response = api.userRegister(AuthUser(login,password))
            if (response.isSuccessful) {
                // TODO: 9/7/2021
            } else {
                withContext(Dispatchers.IO) {
                    val x = response.errorBody()?.string()
                    val errorMessageFromApi =
                        moshi.fromJson(x)?.message
                    errorMessageFromApi?.let {
                        throw UserRegisterError(it)
                    }
                }
            }
        } catch (e: Exception) {
            var message = androidContext.getString(R.string.show_unknown_error)
            if (e is UserRegisterError) {
                e.message?.let {
                    message = it
                }
            }
            throw Exception(message)
        }
    }

    override suspend fun userLogin(login: String, password: String) {
        try {
            val response = api.userLogin(AuthUser(login,password))
            if (response.isSuccessful) {
                // TODO: 9/8/2021
            } else {
                withContext(Dispatchers.IO) {
                    val x = response.errorBody()?.string()
                    val errorMessageFromApi =
                        moshi.fromJson(x)?.message
                    errorMessageFromApi?.let {
                        throw UserRegisterError(it)
                    }
                }
            }
        } catch (e: Exception) {
            var message = androidContext.getString(R.string.show_unknown_error)
            if (e is UserRegisterError) {
                e.message?.let {
                    message = it
                }
            }
            throw Exception(message)
        }
    }

    /**
     * Thrown when there was a error registering a new user
     *
     * @property message user ready error message
     * @property cause the original cause of this exception
     */
    class UserRegisterError(message: String) : Exception(message)

    private fun moshiResponseMapper() = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
        .adapter(ErrorRegResponse::class.java)


}