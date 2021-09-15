package tk.quietdev.level1.data.remote.models


data class GetUserResponse(
    val code: Int,
    val data: RemoteData,
    val message: String,
    val status: String
)

data class RemoteData(
    val user: ApiUser
)

