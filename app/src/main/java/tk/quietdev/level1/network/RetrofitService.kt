package tk.quietdev.level1.network

import retrofit2.Response
import retrofit2.http.GET
import tk.quietdev.level1.models.UserModel

interface RetrofitService {

    @GET("./users")
    suspend fun getUsers() : Response<List<UserModel>>

}
