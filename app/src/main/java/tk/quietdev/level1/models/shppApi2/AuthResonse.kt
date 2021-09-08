package tk.quietdev.level1.models.shppApi2

data class AuthResonse(
    val code: Int,
    val `data`: Data,
    val message: String,
    val status: String
) {
    enum class Status {
        ONGOING, BAD, OK, NULL
    }
}