package tk.quietdev.level1.data.remote.test

import tk.quietdev.level1.data.remote.models.ApiUser

data class REMOVECONTACTRESPONSE(
    val code: Int,
    val `data`: RespTest,
    val message: String,
    val status: String
)


data class RespTest(
    val contacts: List<ApiUser>
)