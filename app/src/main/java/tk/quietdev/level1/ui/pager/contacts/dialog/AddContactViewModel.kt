package tk.quietdev.level1.ui.pager.contacts.dialog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.repository.Repository
import javax.inject.Inject

@HiltViewModel
class AddContactViewModel @Inject constructor(private val db: Repository) : ViewModel() {

    val newUser = MutableLiveData<UserModel>()

    fun addNewUser(
        name: String,
        surname: String,
        occupation: String,
    ) {

    }

    private fun addUserToDb(userModel: UserModel) : UserModel = db.addUser(userModel)
}