package tk.quietdev.level1.data.remote


import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import tk.quietdev.level1.data.remote.models.AuthUser
import tk.quietdev.level1.data.remote.models.AuthResponse

interface ShppApi {

    companion object {
        const val BASE_URL = "http://188.40.127.78:7777/api/"
    }


    @POST("./user/register")
    suspend fun userRegister(@Body requestBody: AuthUser): Response<AuthResponse>

    @POST("./user/login")
    suspend fun userLogin(@Body requestBody: AuthUser): Response<AuthResponse>


}