package tk.quietdev.level1.ui.pager.contacts.dialog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import tk.quietdev.level1.database.FakeDatabase
import tk.quietdev.level1.models.UserModel
import javax.inject.Inject

@HiltViewModel
class AddContactViewModel @Inject constructor(private val db: FakeDatabase) : ViewModel() {

    val newUser = MutableLiveData<UserModel>()

    fun addNewUser(
        name: String,
        surname: String,
        occupation: String,
    ) {
        val newUser = UserModel(
            userName = "$name $surname",
            email = "$name.$surname@mail.fake",
            occupation = occupation
        )
        this.newUser.value = addUserToDb(newUser)
    }

    private fun addUserToDb(userModel: UserModel) : UserModel = db.addUser(userModel)
}