package tk.quietdev.level1.models

import com.fasterxml.jackson.annotation.JsonProperty

data class User(
    var userName: String? = null,
    var email: String,
    var occupation: String = "Not provided",
    @JsonProperty("address") var physicalAddress: String = "Not provided",
    var picture: String = "https://i.pravatar.cc/150?u=${email}",
    var password: String = "11111",
    var birthDate: String? = null,
    var phone: String? = null,
)

