package tk.quietdev.level1.data.remote.models

data class AuthResponse(
    val code: Int,
    val data: UserTokenData,
    val message: String,
    val status: String
)

data class UserTokenData(
    val accessToken: String,
    val user: ApiUser
)