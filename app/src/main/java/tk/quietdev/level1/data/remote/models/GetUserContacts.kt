package tk.quietdev.level1.data.remote.test

import tk.quietdev.level1.data.remote.models.ApiUser

class GetUserContacts(
    val code: Int,
    val data: DataUserContacts,
    val message: String,
    val status: String
)

class DataUserContacts(
    val contacts: List<ApiUser>
)