package tk.quietdev.level1.data.remote.test

import tk.quietdev.level1.data.remote.models.ApiUser

data class GetAllUsersResponse(
    val code: Int,
    val data: Data,
    val message: String,
    val status: String
)


data class Data(
    val users: List<ApiUser>
)