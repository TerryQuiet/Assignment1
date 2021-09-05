package tk.quietdev.level1.ui.pager.settings.edit

import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.repository.Repository
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(private val db: Repository) : ViewModel() {

    lateinit var currentUserModel: UserModel
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
        val updatedUser = currentUserModel.copy(
            userName = userName,
            email = email,
            occupation = occupation,
            physicalAddress = physicalAddress,
            birthDate = birthDate,
            phone = phone,
            pictureUri = pictureUri
        )
        if (currentUserModel != updatedUser) {
            currentUserModel = updatedUser
            db.updateUser(currentUserModel)
            return true
        }
        return false
    }
}