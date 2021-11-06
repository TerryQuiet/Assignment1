package tk.quietdev.level1.ui.main.myprofile.edit

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import tk.quietdev.level1.common.Resource
import tk.quietdev.level1.domain.models.UserModel
import tk.quietdev.level1.usecase.EditUserUseCase

import tk.quietdev.level1.usecase.FlowCurrentUserUseCase
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val currentUserUseCase: FlowCurrentUserUseCase,
    private val editUserUseCase: EditUserUseCase
) : ViewModel() {

    private val _currentUserModel = MutableLiveData<Resource<UserModel>>(Resource.Loading(null))
    val currentUserModel: LiveData<Resource<UserModel>> get() = _currentUserModel
    var localPictureUri: Uri? = null

    init {
        subscribeCurrentUser()
    }

    fun updateUser(
        userName: String,
        email: String,
        occupation: String,
        physicalAddress: String,
        birthDate: String,
        phone: String,
        //pictureUri: String? = null
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
                //pictureUri = pictureUri
            )
            if (currentUser != updatedUser) {
                Log.d("TAG", "updateUser: $currentUser \n $updatedUser")
                Log.wtf("TAG", "updateUser: ")
                viewModelScope.launch {
                    editUserUseCase.invoke(updatedUser).onEach {
                        when (it) {
                            is Resource.Loading -> _currentUserModel.value =
                                Resource.Loading(_currentUserModel.value?.data)
                            //is Resource.Success -> _currentUserModel.value = Resource.Success(_currentUserModel.value?.data!!)
                        }
                    }.launchIn(viewModelScope)
                }
            }
        }
    }

    private fun subscribeCurrentUser() {
        viewModelScope.launch {
            currentUserUseCase.invoke().onEach {
                _currentUserModel.value = it
            }.launchIn(viewModelScope)
        }
    }


    fun getShortDate(ts: Long?): String {
        if (ts == null) return ""
        //Get instance of calendar
        val calendar = Calendar.getInstance(Locale.getDefault())
        //get current date from ts
        calendar.timeInMillis = ts
        //return formatted date
        return android.text.format.DateFormat.format("yyyy-MM-dd", calendar).toString()
    }

}