package tk.quietdev.level1.data.remote


import retrofit2.Response
import retrofit2.http.*
import tk.quietdev.level1.data.remote.models.*
import tk.quietdev.level1.data.remote.test.GetAllUsersResponse
import tk.quietdev.level1.data.remote.test.GetUserContactsResponse

interface ShppApi {

    companion object {
        const val BASE_URL = "http://188.40.127.78:7777/api/"
    }

    @POST("./user/register")
    suspend fun userRegister(@Body requestBody: AuthUser): Response<AuthResponse>

    @POST("./user/login")
    suspend fun userLogin(@Body requestBody: AuthUser): Response<AuthResponse>

    @GET("./user/profile")
    suspend fun getCurrentUser(@Header("Authorization") token : String): Response<GetUserResponse>

    @GET("./users")
    suspend fun getAllUsers(@Header("Authorization") token : String): Response<GetAllUsersResponse>

    @GET("./user/contacts")
    suspend fun getCurrentUserContacts(@Header("Authorization") token : String): Response<GetUserContactsResponse>

    @POST("./user/profile")
    suspend fun updateUser(@HeaderMap headers: Map<String, String>, @Body updatedApiUser: RemoteData): Response<GetUserResponse>

    @POST("./user/contact/add")
    suspend fun addUserContact(@HeaderMap headers: Map<String, String>, @Body requestBody: ApiUserContactManipulation): Response<GetUserContactsResponse>

    @POST("./user/contact/delete")
    suspend fun removeUserContact(@HeaderMap headers: Map<String, String>, @Body requestBody: ApiUserContactManipulation): Response<GetUserContactsResponse>



}