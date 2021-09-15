package tk.quietdev.level1.data.remote


import retrofit2.Response
import retrofit2.http.*
import tk.quietdev.level1.data.remote.models.*
import tk.quietdev.level1.data.remote.test.GetAllUsersResponse
import tk.quietdev.level1.data.remote.test.GetUserContacts

interface ShppApi {

    companion object {
        const val BASE_URL = "http://188.40.127.78:7777/api/"
    }

    @POST("./user/register")
    suspend fun userRegister(@Body requestBody: AuthUser): Response<AuthResponse>

    @POST("./user/login")
    suspend fun userLogin(@Body requestBody: AuthUser): Response<AuthResponse>

    @GET("./user/profile")
    suspend fun getCurrentUser(@Header("Authorization") token : String): Response<GetUser>

    @GET("./users")
    suspend fun getAllUsers(@Header("Authorization") token : String): Response<GetAllUsersResponse>

    @GET("./user/contacts")
    suspend fun getCurrentUserContacts(@Header("Authorization") token : String): Response<GetUserContacts>

    @POST("./user/profile")
    suspend fun updateUser(@HeaderMap headers: Map<String, String>, @Body updatedApiUser: RemoteData): Response<GetUser>

    @POST("./user/contact/add")
    suspend fun addUserContact(@HeaderMap headers: Map<String, String>, @Body contactId: Int): Response<GetUserContacts>

    @POST("./user/contact/delete")
    suspend fun deleteUserContact(@HeaderMap headers: Map<String, String>, @Body contactId: Int): Response<GetUserContacts>



}