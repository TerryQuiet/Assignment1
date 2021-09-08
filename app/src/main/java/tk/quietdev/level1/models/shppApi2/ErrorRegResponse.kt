package tk.quietdev.level1.models.shppApi2

data class ErrorRegResponse(
    val code: Int,
    val `data`: List<Any>,
    val message: String,
    val status: String
)