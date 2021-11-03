package tk.quietdev.level1.ui.authorization.login


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import tk.quietdev.level1.common.Resource
import tk.quietdev.level1.usecase.UserLoginUseCase
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userLoginUseCase: UserLoginUseCase
) : ViewModel() {

    private val _dataState: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    val dataState: LiveData<Resource<Boolean>>
        get() = _dataState

    var email: String = ""

    private var loginJob: Job? = null

    fun tokenLogin() {
        _dataState.value = Resource.Success(true)
    }

    fun passwordLogin(email: String, passwd: String) {
        viewModelScope.launch {
            val response = userLoginUseCase.invoke(email = email, password = passwd)
            _dataState.value = response
        }
    }

    private fun login() {

    }
}