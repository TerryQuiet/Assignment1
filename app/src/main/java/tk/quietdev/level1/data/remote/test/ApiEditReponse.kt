package tk.quietdev.level1.data.remote.test

import tk.quietdev.level1.data.remote.models.ApiUser

data class ApiEditReponse(
    val code: Int,
    val `data`: InData,
    val message: String,
    val status: String
)

data class InData(
    val user: ApiUser
)