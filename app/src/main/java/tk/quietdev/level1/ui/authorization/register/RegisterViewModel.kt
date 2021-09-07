package tk.quietdev.level1.ui.authorization.register


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tk.quietdev.level1.models.shppApi.AuthUser
import tk.quietdev.level1.models.shppApi.RegisterResponse
import tk.quietdev.level1.repository.Repository
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val isErrorShown = MutableLiveData(false) // disable regButton
    val regResponse = MutableLiveData(RegisterResponse.Status.NULL)
    var errorMessage = ""
    //the fields need to be here

    fun regUser(email: String, passwd: String) {
        val userToReg = AuthUser(email = email, password = passwd)
        viewModelScope.launch {
            try {
                regResponse.value = RegisterResponse.Status.ONGOING
                repository.userRegistration(userToReg)
                regResponse.value = RegisterResponse.Status.OK
            } catch (e: Exception) {
                e.message?.let {
                    errorMessage = it
                }
                regResponse.value = RegisterResponse.Status.BAD
            } finally {
                regResponse.value = RegisterResponse.Status.NULL
            }
        }
    }

}