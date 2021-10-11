package tk.quietdev.level1.data.remote.models

data class ApiUpdatedUser(
    val user: ApiInternalUpdatedUser
)

data class ApiInternalUpdatedUser(
    val name: String?,
    val phone: String?,
    val address: String?,
    val career: String?,
    val birthday: String?,
)
