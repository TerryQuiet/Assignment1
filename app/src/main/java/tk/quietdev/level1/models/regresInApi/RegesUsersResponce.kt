package tk.quietdev.level1.models.regresInApi

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class RegesUsersResponce(
    @Json(name = "data") val users: List<UserModel>,
    val page: Int,
    @Json(name = "per_page") val perPage: Int,
    val support: Support,
    val total: Int,
    @Json(name = "total_pages") val totalPages: Int
)