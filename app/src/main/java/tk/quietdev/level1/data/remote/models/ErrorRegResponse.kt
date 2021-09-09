package tk.quietdev.level1.data.remote.models

data class ErrorRegResponse(
    val code: Int,
    val data: List<Any>,
    val message: String,
    val status: String
)