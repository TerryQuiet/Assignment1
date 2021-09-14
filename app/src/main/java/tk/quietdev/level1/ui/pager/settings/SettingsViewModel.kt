package tk.quietdev.level1.ui.pager.settings

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel

import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.repository.RemoteApiRepository

import tk.quietdev.level1.repository.Repository
import tk.quietdev.level1.utils.Resource
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(repository: Repository) : ViewModel() {

   // val currentUserModel : LiveData<UserModel> = repository.currentUserFlow().asLiveData()
   // val currentUserModel : LiveData<UserModel> = (repository as RemoteApiRepository).currentUserFlow2().asLiveData()
    val currentUserModel : LiveData<Resource<UserModel>> = (repository as RemoteApiRepository).currentUserFlow3().asLiveData()
}