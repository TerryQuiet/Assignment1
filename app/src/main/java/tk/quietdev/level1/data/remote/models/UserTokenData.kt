package tk.quietdev.level1.data.remote.models

data class UserTokenData(
    val accessToken: String,
    val user: ApiUser
)