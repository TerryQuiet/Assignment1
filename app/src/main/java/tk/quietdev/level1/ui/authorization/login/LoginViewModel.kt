package tk.quietdev.level1.ui.authorization.login


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.repository.Repository
import tk.quietdev.level1.utils.DataState
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    val isErrorShown = MutableLiveData(false) // disable regButton
    private val _dataState: MutableLiveData<DataState<UserModel>> = MutableLiveData()

    val dataState: LiveData<DataState<UserModel>>
        get() = _dataState

    fun loginUser(email: String, passwd: String) {
        viewModelScope.launch {
           repository.userLogin(email, passwd).onEach {
               _dataState.value = it
           }.launchIn(viewModelScope)
        }


    }
}