package tk.quietdev.level1.ui.pager.contacts.list.addcontacts


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.repository.Repository
import tk.quietdev.level1.utils.ListState
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddContactListViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var userList = repository.getAllUsersFlow().asLiveData()

    private var deletedUserPosition: Int? = null
    private var userIdToRemove = TreeSet<Int>()

    var listState = MutableLiveData(ListState.NORMAL)

    fun addUserBack(id: Int) {

    }

    fun removeUser(userModel: UserModel, position: Int) {

    }

    fun addNewUser(userModel: UserModel) {

    }

    fun toggleUserSelected(userId: Int) {
        val isUserRemovedFromList = userIdToRemove.remove(userId)
        if (!isUserRemovedFromList) {
            userIdToRemove.add(userId)
        }
        listState.value = if (userIdToRemove.isNotEmpty()) ListState.MULTISELECT else ListState.NORMAL
    }

    fun removeUsers() {

    }

    fun isItemSelected(id: Int): Boolean {
        return userIdToRemove.contains(id)
    }

    fun addUserContact(userModel: UserModel) {
        viewModelScope.launch {
            repository.addUserContact(userModel)
        }
    }


}

