package tk.quietdev.level1.ui.pager.settings

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.repository.Repository
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var currentUserModel: MutableLiveData<UserModel> = MutableLiveData()

    fun getCurrentUser() {
        viewModelScope.launch {
            repository.getCurrentUser().onEach {
                currentUserModel.value = it
            }.launchIn(viewModelScope)
        }
    }

    fun getCurrentUserTest() {
        Log.d("SSS", "getCurrentUserTest: ")
        viewModelScope.launch {
            repository.getCurrentUser().launchIn(viewModelScope)
        }

    }
}