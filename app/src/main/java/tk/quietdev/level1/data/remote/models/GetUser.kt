package tk.quietdev.level1.data.remote.models

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

data class GetUser(
    val code: Int,
    val data: RemoteData,
    val message: String,
    val status: String
)

data class RemoteData(
    val user: ApiUser
)

