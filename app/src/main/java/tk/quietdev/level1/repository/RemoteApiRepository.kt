package tk.quietdev.level1.repository

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import tk.quietdev.level1.R
import tk.quietdev.level1.data.db.RoomUserDao
import tk.quietdev.level1.data.remote.ShppApi
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.data.remote.models.AuthResponse
import tk.quietdev.level1.data.remote.models.AuthUser
import tk.quietdev.level1.data.remote.models.ErrorRegResponse
import kotlin.reflect.KSuspendFunction1

class RemoteApiRepository(
    @ApplicationContext private val androidContext: Context,
    private val api: ShppApi,
    private val db: RoomUserDao
) : Repository {

    private val apiErrorMapper by lazy { moshiErrorResponseMapper() }

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
        val response = userAuth(AuthUser(login,password), api::userRegister)
    }

    override suspend fun userLogin(login: String, password: String) {
        val response = userAuth(AuthUser(login,password), api::userLogin)
    }

    private suspend fun userAuth(user: AuthUser, method: KSuspendFunction1<AuthUser, Response<AuthResponse>>): Response<AuthResponse>? {
        try {
            val response = method(user)
            if (response.isSuccessful) {
                return method(user)
            } else {
                withContext(Dispatchers.IO) {
                    val x = response.errorBody()?.string()
                    val errorMessageFromApi =
                        apiErrorMapper.fromJson(x)?.message
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
        return null
    }

    /**
     * Thrown when there was a error registering a new user
     *
     * @property message user ready error message
     * @property cause the original cause of this exception
     */
    class UserRegisterError(message: String) : Exception(message)

    private fun moshiErrorResponseMapper() = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
        .adapter(ErrorRegResponse::class.java)


}