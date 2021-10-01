package tk.quietdev.level1.ui.authorization.login


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import tk.quietdev.level1.data.repository.Repository
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.utils.Resource
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _dataState: MutableLiveData<Resource<UserModel?>> = MutableLiveData()
    val dataState: LiveData<Resource<UserModel?>>
        get() = _dataState

    private var loginJob: Job? = null

    fun tokenLogin() {
        login(repository.currentUserFlow())
    }

    fun passwordLogin(email: String, passwd: String) {
        login(repository.userLogin(email, passwd))
    }

    private fun login(currentUserFlow: Flow<Resource<UserModel?>>) {
        loginJob = currentUserFlow.onEach {
            _dataState.value = it
            if (it is Resource.Success) {
                loginJob?.cancel()
            }
        }.launchIn(viewModelScope)
    }
}