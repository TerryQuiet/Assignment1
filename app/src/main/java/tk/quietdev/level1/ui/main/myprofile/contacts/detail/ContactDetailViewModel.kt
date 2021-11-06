package tk.quietdev.level1.ui.main.myprofile.contacts.detail

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
import tk.quietdev.level1.usecase.FlowUserByIdUseCase
import javax.inject.Inject

@HiltViewModel
class ContactDetailViewModel @Inject constructor(
    private val flowUserByIdUseCase: FlowUserByIdUseCase
) : ViewModel() {

    private val _currentUserModel = MutableLiveData<Resource<UserModel>>(Resource.Loading(null))
    val currentUserModel: LiveData<Resource<UserModel>> get() = _currentUserModel

    fun init(id: Int) {
        viewModelScope.launch {
            flowUserByIdUseCase.invoke(id).onEach {
                _currentUserModel.value = it
            }.launchIn(viewModelScope)
        }
    }
}