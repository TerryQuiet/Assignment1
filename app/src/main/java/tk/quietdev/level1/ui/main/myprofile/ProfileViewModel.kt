package tk.quietdev.level1.ui.main.myprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import tk.quietdev.level1.data.repository.Repository
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(repository: Repository) : ViewModel() {

    val currentUserModel = repository.currentUserFlow().asLiveData()
}