package tk.quietdev.level1.models


data class Account(
    val address: String = "Not provided",
    val birthDate: String = "Not provided",
    val email: String,
    val occupation: String = "Not provided",
    val phone: String = "Not provided",
    val userName: String = "Not provided",
    val password: String = "11111"
)
