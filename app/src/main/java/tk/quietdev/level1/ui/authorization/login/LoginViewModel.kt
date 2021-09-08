package tk.quietdev.level1.ui.authorization.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tk.quietdev.level1.models.shppApi2.AuthResonse
import tk.quietdev.level1.models.shppApi2.AuthUser
import tk.quietdev.level1.repository.Repository
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    val isErrorShown = MutableLiveData(false) // disable regButton
    val regResponse = MutableLiveData(AuthResonse.Status.NULL)
    var errorMessage = ""
    //the fields need to be here

    fun regUser(email: String, passwd: String) {
        viewModelScope.launch {
            try {
                regResponse.value = AuthResonse.Status.ONGOING
                repository.userLogin(email, passwd)
                regResponse.value = AuthResonse.Status.OK
            } catch (e: Exception) {
                e.message?.let {
                    errorMessage = it
                }
                regResponse.value = AuthResonse.Status.BAD
            } finally {
                regResponse.value = AuthResonse.Status.NULL
            }
        }
    }
}