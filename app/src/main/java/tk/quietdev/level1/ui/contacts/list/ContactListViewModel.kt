package tk.quietdev.level1.ui.contacts.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tk.quietdev.level1.database.FakeDatabase
import tk.quietdev.level1.models.User

class ContactListViewModel(
    private val db: FakeDatabase
) : ViewModel() {

    var userList = MutableLiveData<MutableList<User>>()

    var deletedUserPosition: Int? = null

    init {
        userList = MutableLiveData(db.getUserList(11).toMutableList())
    }

    fun addUserBack(id: Int) {
        val user = db.getUserWithNoValidation(id)
        user?.let {
            userList.value?.add(deletedUserPosition!!, user)
            deletedUserPosition = null
        }
    }

    fun removeUser(user: User, position: Int) {
       /* val removedUser = userList.value?.remove(user)
        Log.d(Const.TAG, "removeUser: $removedUser ")
        removedUser?.let {
            deletedUserPosition = position
            updateLiveData()
        }*/

        val listUsersTemp: MutableList<User> = userList.value as MutableList<User>
        if (listUsersTemp.isNotEmpty()) {
            listUsersTemp.remove(user)
            deletedUserPosition = position
            userList.value = listUsersTemp
        }
    }

    fun getUserById(id: Int): User? {
        return db.getUserWithNoValidation(id)
    }

    private fun addNewUserToDatabase(user: User) {
        db.addUser(user)
        userList.value?.add(user)
        updateLiveData()
    }

    private fun updateLiveData() {
       // userList.postValue(userList.value)
        userList.value = userList.value
    }

}