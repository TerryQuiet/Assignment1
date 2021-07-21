package tk.quietdev.level1.models

data class User(
    var userName: String? = null,
    var email: String,
    var occupation: String = "Not provided",
    var physicalAddress: String = "Not provided",
    var picture: String = "https://i.pravatar.cc/150?u=${email}",
    var password: String = "11111"
)


