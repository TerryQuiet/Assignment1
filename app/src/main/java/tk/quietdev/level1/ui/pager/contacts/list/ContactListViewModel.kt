package tk.quietdev.level1.ui.pager.contacts.list


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.repository.RemoteApiRepository
import tk.quietdev.level1.repository.Repository
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ContactListViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var userList = repository.getCurrentUserContactsFlow().asLiveData()

    private var deletedUserPosition: Int? = null
    private var userIdToRemove = TreeSet<Int>()

    var listState = MutableLiveData(ListState.NORMAL)

    fun addUserBack(id: Int) {

    }

    fun removeUser(userModel: UserModel, position: Int) {

    }

    fun addNewUser(userModel: UserModel) {

    }

    fun watchUserContacts() {
        userList = repository.getCurrentUserContactsFlow().asLiveData()
    }

    fun watchAllUsers() {
        userList = repository.getAllUsersFlow().asLiveData()
    }


    fun toggleUserSelected(userId: Int) {
        val isUserRemovedFromList = userIdToRemove.remove(userId)
        if (!isUserRemovedFromList) {
            userIdToRemove.add(userId)
        }
        listState.value = if (userIdToRemove.isNotEmpty()) ListState.DELETION else ListState.NORMAL
    }

    fun removeUsers() {

    }

    fun isItemSelected(id: Int): Boolean {
        return userIdToRemove.contains(id)
    }

    fun addState() {
        listState.value = ListState.ADDITION
    }

}

enum class ListState {
    NORMAL, DELETION, ADDITION
}