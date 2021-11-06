package tk.quietdev.level1.ui.authorization.login


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import tk.quietdev.level1.common.Resource
import tk.quietdev.level1.usecase.AutoLoginUseCase
import tk.quietdev.level1.usecase.UserLoginUseCase
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userLoginUseCase: UserLoginUseCase,
    private val autoLoginUseCase: AutoLoginUseCase
) : ViewModel() {

    private val _dataState: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    val dataState: LiveData<Resource<Boolean>>
        get() = _dataState

    var email: String = ""

    fun tokenLogin() {
        viewModelScope.launch {
            _dataState.value = autoLoginUseCase.invoke()
        }
    }

    fun passwordLogin(email: String, passwd: String) {
        viewModelScope.launch {
            userLoginUseCase.invoke(email = email, password = passwd).onEach {
                _dataState.value = it
            }.launchIn(viewModelScope)
        }
    }

}