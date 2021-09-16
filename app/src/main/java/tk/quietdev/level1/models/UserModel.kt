package tk.quietdev.level1.models

import android.os.Parcelable

import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    var id: Int,
    var userName: String? = null,
    var email: String,
    var occupation: String? = "Not provided",
    var physicalAddress: String? = "Not provided",
    var pictureUri: String? = "https://i.pravatar.cc/150?u=${email}",
    var password: String = "11111",
    var birthDate: String? = null,
    var phone: String? = null,
) : Parcelable
