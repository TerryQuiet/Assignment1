package tk.quietdev.level1.data.remote


import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import tk.quietdev.level1.data.remote.models.*

interface ShppApi {

    companion object {
        const val BASE_URL = "http://188.40.127.78:7777/api/"
    }

    @POST("./user/register")
    suspend fun userRegister(@Body requestBody: AuthUser): Response<AuthResponse>

    @POST("./user/login")
    suspend fun userLogin(@Body requestBody: AuthUser): Response<AuthResponse>

    @GET("./user/profile")
    suspend fun getCurrentUser(): Response<GetUserResponse>

    @GET("./users")
    suspend fun getAllUsers(): Response<GetAllUsersResponse>

    @GET("./user/contacts")
    suspend fun getCurrentUserContacts(): Response<GetUserContactsResponse>

    @POST("./user/profile/edit")
    suspend fun updateUser(@Body updatedApiUser: ApiUpdatedUser): Response<GetUserResponse>

    @POST("./user/contact/add")
    suspend fun addUserContact(
        @Body requestBody: ApiUserContactManipulation
    ): Response<GetUserContactsResponse>

    @POST("./user/contact/delete")
    suspend fun removeUserContact(@Body requestBody: ApiUserContactManipulation)
            : Response<GetUserContactsResponse>


}