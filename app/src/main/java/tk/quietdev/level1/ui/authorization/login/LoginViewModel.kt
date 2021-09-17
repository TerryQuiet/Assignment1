package tk.quietdev.level1.ui.authorization.login


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.repository.Repository
import tk.quietdev.level1.utils.Resource
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    val isErrorShown = MutableLiveData(false) // disable regButton

    private val _dataState: MutableLiveData<Resource<UserModel?>> = MutableLiveData()

    val dataState: LiveData<Resource<UserModel?>>
        get() = _dataState
    
    private var loginJob: Job? = null

    fun loginUser(email: String, passwd: String) {
    loginJob = repository.userLogin(email, passwd).onEach {
            _dataState.value = it
            Log.d("TAG", "loginUser:VM $it")
            if (it is Resource.Success) {
                loginJob?.cancel()
            }
        }.launchIn(viewModelScope)
    }
}