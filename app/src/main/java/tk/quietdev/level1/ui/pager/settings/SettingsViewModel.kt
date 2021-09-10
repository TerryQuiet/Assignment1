package tk.quietdev.level1.ui.pager.settings

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.repository.Repository
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _currentUserModel: MutableLiveData<UserModel> = MutableLiveData()
    val currentUserModel : LiveData<UserModel> = _currentUserModel

    fun initRoomObservers() {

        viewModelScope.launch {
            repository.currentUserFlow().onEach {
                _currentUserModel.value = it
            }.launchIn(viewModelScope)
          //  repository.cacheCurrentUserContactsFromApi()
        }
    }
}