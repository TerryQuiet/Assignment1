package tk.quietdev.level1.ui.contacts.dialog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tk.quietdev.level1.database.FakeDatabase
import tk.quietdev.level1.models.User

class AddContactViewModel(private val db: FakeDatabase) : ViewModel() {

    val newUser = MutableLiveData<User>()

    fun addNewUser(
        name: String,
        surname: String,
        occupation: String,
    ) {
        val newUser = User(
            userName = "$name $surname",
            email = "$name.$surname@mail.fake",
            occupation = occupation
        )
        this.newUser.value = addUserToDb(newUser)
    }

    private fun addUserToDb(user: User) : User = db.addUser(user)
}