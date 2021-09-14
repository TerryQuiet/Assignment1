package tk.quietdev.level1.ui.pager.contacts


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import tk.quietdev.level1.models.UserModel
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class ContactsSharedViewModel @Inject constructor(): ViewModel() {
    val newUser = MutableLiveData<UserModel>(null)
}