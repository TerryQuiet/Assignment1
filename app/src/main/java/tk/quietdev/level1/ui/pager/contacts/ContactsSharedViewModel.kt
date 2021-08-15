package tk.quietdev.level1.ui.pager.contacts


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tk.quietdev.level1.models.UserModel

class ContactsSharedViewModel : ViewModel() {
    val newUser = MutableLiveData<UserModel>(null)
    val updatedUser = MutableLiveData<UserModel>(null)
}