package tk.quietdev.level1.ui.pager.contacts.dialog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tk.quietdev.level1.database.FakeDatabase
import tk.quietdev.level1.models.UserModel

class AddContactViewModel(private val db: FakeDatabase) : ViewModel() {

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