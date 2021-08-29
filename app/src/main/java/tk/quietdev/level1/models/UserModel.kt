package tk.quietdev.level1.models

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    var id: Int? = null,
    var userName: String? = null,
    var email: String,
    var occupation: String = "Not provided",
    @JsonProperty("address") var physicalAddress: String = "Not provided",
    @JsonProperty("picture") var pictureUri: String = "https://i.pravatar.cc/150?u=${email}",
    var password: String = "11111",
    var birthDate: String? = null,
    var phone: String? = null,
    var isToRemoveChecked: Boolean = false // FIXME: 8/30/2021 NOT SUPPOSE TO BE HERE 
) : Parcelable
