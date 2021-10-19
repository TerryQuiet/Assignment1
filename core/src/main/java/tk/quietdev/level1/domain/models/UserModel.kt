package tk.quietdev.level1.domain.models


data class UserModel(
    var id: Int,
    var userName: String? = null,
    var email: String,
    var occupation: String? = "Not provided",
    var physicalAddress: String? = "Not provided",
    var pictureUri: String? = "https://i.pravatar.cc/150?u=${email}",
    var birthDate: String? = null,
    var phone: String? = null,
)
