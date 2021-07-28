package tk.quietdev.level1.ui.contacts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tk.quietdev.level1.database.FakeDatabase
import tk.quietdev.level1.databinding.DialogAddContactBinding
import tk.quietdev.level1.models.User


class ContactsViewModel : ViewModel() {

    private val db = FakeDatabase

    private var deletedUserPosition = 0
    val userList = MutableLiveData(db.getUserList().toMutableList())
    private val deletedUsers = mutableMapOf<String, Boolean>()


    private fun updateLiveData() {
        userList.postValue(userList.value)
    }

    private fun addNewUserToDatabase(user: User) {
        db.addUser(user)
        userList.value?.add(user.email)
        updateLiveData()
    }

    fun addUserBack(email :String) {
        val isRecoverable = deletedUsers[email]
        isRecoverable?.let {
            if (it) {
                userList.value?.add(deletedUserPosition, email)
                deletedUsers.remove(email)
                updateLiveData()
            }
        }
    }

    fun removeUser(email: String?) {
        val position = userList.value?.indexOf(email)
        position?.let {
            deletedUsers[userList.value?.removeAt(it)!!] = true
            deletedUserPosition = it
            updateLiveData()
        }
    }


    fun onDialogAddClicked(dialogBinding: DialogAddContactBinding) {
        val name = dialogBinding.etName.text.toString()
        val surname = dialogBinding.etSurname.text.toString()
        val occupation = dialogBinding.etOccupation.text.toString()

        val user = User(
            userName = "$name $surname",
            email = "$name.$surname@mail.fake",
            occupation = occupation
        )
        addNewUserToDatabase(user)
    }

    fun getUser(email: String?): User? {
       return db.getUserWithNoValidation(email)
    }

    fun setUserRecoverable(email: String, boolean: Boolean) {
        deletedUsers[email] = boolean
    }

}