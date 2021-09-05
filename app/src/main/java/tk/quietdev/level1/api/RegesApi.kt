package tk.quietdev.level1.api

import retrofit2.Response
import retrofit2.http.GET
import tk.quietdev.level1.models.regresInApi.RegesUsersResponce

interface RegesApi {

    companion object {
        const val BASE_URL = "https://restcountries.eu/rest/v2/"
    }

    @GET("users")
    suspend fun getUsers(
        //@Query(value = "accessToken") token : String = s.token
    ) : Response<RegesUsersResponce>


}