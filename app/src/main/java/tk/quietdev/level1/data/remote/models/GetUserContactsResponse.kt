package tk.quietdev.level1.data.remote.models

class GetUserContactsResponse(
    val code: Int,
    val data: DataUserContacts,
    val message: String,
    val status: String
)

class DataUserContacts(
    val contacts: List<ApiUser>
)