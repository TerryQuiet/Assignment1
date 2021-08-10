package tk.quietdev.level1.ui.pager.contacts


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tk.quietdev.level1.models.User


/**
 * Here I have list of Emails to show in recycleView (I'm not showing all I have in DB)
 */

class ContactsSharedViewModel : ViewModel() {

    val newUser = MutableLiveData<User>(null)
    val updatedUser = MutableLiveData<User>(null)

}