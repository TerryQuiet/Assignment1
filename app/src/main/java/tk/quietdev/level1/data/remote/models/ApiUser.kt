package tk.quietdev.level1.data.remote.models

import com.squareup.moshi.Json

data class ApiUser(
    val address: String?,
    val birthday: String?,
    val career: String?,
    @Json(name = "created_at") val createdAt: String? = null,
    val email: String,
    val id: Int,
    val name: String?,
    val phone: String?,
    @Json(name = "updated_at") val updatedAt: String? = null
)