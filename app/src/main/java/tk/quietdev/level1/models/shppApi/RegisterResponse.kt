package tk.quietdev.level1.models.shppApi

class RegisterResponse(
    val code: Int,
    val `data`: List<Any>,
    val message: String,
    val status: String
) {
    enum class Status {
        ONGOING, BAD, OK, NULL
    }
}