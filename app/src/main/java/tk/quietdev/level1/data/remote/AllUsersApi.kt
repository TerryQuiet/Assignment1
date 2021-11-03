package tk.quietdev.level1.data.remote


import retrofit2.Response
import retrofit2.http.GET
import tk.quietdev.level1.data.remote.models.GetAllUsersResponse

interface AllUsersApi {

    @GET("./users")
    suspend fun getAllUsers(): Response<GetAllUsersResponse>

}