package tk.quietdev.level1.ui.main.myprofile

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
import tk.quietdev.level1.usecase.FlowCurrentUserUseCase
import tk.quietdev.level1.usecase.RefreshCurrentUserUseCase
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val flowCurrentUser: FlowCurrentUserUseCase,
    refreshCurrentUser: RefreshCurrentUserUseCase
) : ViewModel() {

    private val _currentUserModel = MutableLiveData<Resource<UserModel>>(Resource.Loading(null))
    val currentUserModel: LiveData<Resource<UserModel>> get() = _currentUserModel

    init {
        viewModelScope.launch {
            refreshCurrentUser.invoke()
            subscribeCurrentUser()
        }
    }

    private fun subscribeCurrentUser() {
        viewModelScope.launch {
            flowCurrentUser.invoke().onEach {
                _currentUserModel.value = it
            }.launchIn(viewModelScope)
        }
    }


}