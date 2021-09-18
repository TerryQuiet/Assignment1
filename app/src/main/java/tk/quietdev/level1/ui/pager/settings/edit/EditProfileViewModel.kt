package tk.quietdev.level1.ui.pager.settings.edit

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import tk.quietdev.level1.repository.Repository
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class EditProfileViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var currentUserModel = repository.currentUserFlow().asLiveData() as MutableLiveData
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
        pictureUri: String? = null
    ) {
        val currentUser = currentUserModel.value?.data
        currentUser?.let { it ->
            val updatedUser = it.copy(
                userName = userName,
                email = email,
                occupation = occupation,
                physicalAddress = physicalAddress,
                birthDate = birthDate,
                phone = phone,
                pictureUri = pictureUri
            )
            if (currentUser != updatedUser) {
                //this does not work, I don't understand why
           /*currentUserModel =
                    repository.updateUser(updatedUser).asLiveData() as MutableLiveData*/
                repository.updateUser(updatedUser).onEach {
                    it.message = "OnUpdate"
                    currentUserModel.value = it
                }.launchIn(viewModelScope)
            }
        }

    }
}