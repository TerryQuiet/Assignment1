package tk.quietdev.level1.models.regresInApi

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Support(
    val text: String,
    val url: String
)