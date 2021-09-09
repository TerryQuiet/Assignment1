package tk.quietdev.level1.data.remote.test

data class AuthResponse(
    val code: Int,
    val `data`: Data,
    val message: String,
    val status: String
)