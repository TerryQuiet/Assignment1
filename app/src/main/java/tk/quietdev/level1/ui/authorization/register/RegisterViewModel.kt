package tk.quietdev.level1.ui.authorization.register


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import tk.quietdev.level1.common.Resource
import tk.quietdev.level1.data.Repository
import tk.quietdev.level1.domain.models.UserModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _dataState: MutableLiveData<Resource<UserModel?>> = MutableLiveData()
    val dataState: LiveData<Resource<UserModel?>>
        get() = _dataState
    private var registerJob: Job? = null

    fun regUser(email: String, passwd: String) {
        registerJob = repository.userRegistration(email, passwd).onEach {
            _dataState.value = it
            if (it is Resource.Success) {
                registerJob?.cancel()
            }
        }.launchIn(viewModelScope)
    }

}