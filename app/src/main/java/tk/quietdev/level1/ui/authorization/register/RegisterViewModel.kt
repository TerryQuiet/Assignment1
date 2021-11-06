package tk.quietdev.level1.ui.authorization.register


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import tk.quietdev.level1.common.Resource
import tk.quietdev.level1.usecase.UserRegsterUseCase
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRegsterUseCase: UserRegsterUseCase
) : ViewModel() {

    private val _dataState: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    val dataState: LiveData<Resource<Boolean>>
        get() = _dataState

    fun regUser(email: String, passwd: String) {
        viewModelScope.launch {
            userRegsterUseCase.invoke(email = email, password = passwd).onEach {
                _dataState.value = it
            }.launchIn(viewModelScope)
        }
    }

}