package tk.quietdev.level1.ui.contacts.edit

import android.net.Uri
import androidx.lifecycle.ViewModel
import tk.quietdev.level1.database.FakeDatabase
import tk.quietdev.level1.models.User

class EditProfileViewModel(private val db: FakeDatabase) : ViewModel() {

    lateinit var currentUser: User
    var localPictureUri: Uri? = null

    /**
     * @return returns true if user was updated
     */
    fun updateUser(
        userName: String,
        email: String,
        occupation: String,
        physicalAddress: String,
        birthDate: String,
        phone: String,
        pictureUri: String
    ): Boolean {
        val updatedUser = currentUser.copy(
            userName = userName,
            email = email,
            occupation = occupation,
            physicalAddress = physicalAddress,
            birthDate = birthDate,
            phone = phone,
            pictureUri = pictureUri
        )
        if (currentUser != updatedUser) {
            currentUser = updatedUser
            db.updateUser(currentUser)
            return true
        }
        return false
    }
}