package tk.quietdev.level1.ui.pager.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsSharedViewModel @Inject constructor(): ViewModel() {
    val viewMyContactsButtonClicked = MutableLiveData(false)
}