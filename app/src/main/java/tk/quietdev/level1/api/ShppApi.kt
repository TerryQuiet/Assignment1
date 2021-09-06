package tk.quietdev.level1.api


import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import tk.quietdev.level1.models.shppApi.AuthUser
import tk.quietdev.level1.models.shppApi.RegisterResponse

interface ShppApi {

    companion object {
        const val BASE_URL = "http://188.40.127.78:7777/api/"
    }


    @POST("./user/register")
    suspend fun createEmployee(@Body requestBody: AuthUser): Response<RegisterResponse>


}