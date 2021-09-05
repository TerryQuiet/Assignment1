package tk.quietdev.level1.models.regresInApi

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserModel(
    val avatar: String,
    val email: String,
    @Json(name = "first_name") val firstName: String,
    val id: Int,
    @Json(name = "last_name") val lastName: String
)