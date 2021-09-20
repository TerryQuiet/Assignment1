package tk.quietdev.level1.ui.pager.settings.edit

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import tk.quietdev.level1.data.repository.Repository
import tk.quietdev.level1.utils.Const
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*
import javax.inject.Inject

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
                    it.message = Const.ON_USER_UPDATE
                    currentUserModel.value = it
                }.launchIn(viewModelScope)
            }
        }
    }


    fun getShortDate(ts:Long?):String{
        if(ts == null) return ""
        //Get instance of calendar
        val calendar = Calendar.getInstance(Locale.getDefault())
        //get current date from ts
        calendar.timeInMillis = ts
        //return formatted date
        return android.text.format.DateFormat.format("yyyy-MM-dd", calendar).toString()
    }

}