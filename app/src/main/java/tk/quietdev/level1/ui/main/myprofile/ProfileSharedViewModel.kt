package tk.quietdev.level1.ui.main.myprofile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileSharedViewModel @Inject constructor() : ViewModel() {
    val viewMyContactsButtonClicked = MutableLiveData(false)
}