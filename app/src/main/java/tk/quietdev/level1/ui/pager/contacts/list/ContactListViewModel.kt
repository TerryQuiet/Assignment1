package tk.quietdev.level1.ui.pager.contacts.list


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.repository.Repository
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ContactListViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var userList = repository.getAllUsersFlow().asLiveData()
    private var deletedUserPosition: Int? = null
    private var  userIdToRemove = TreeSet<Int>()

    var isRemoveState = MutableLiveData(false)

    init {
        viewModelScope.launch {
            repository.cacheAllUsersFromApi()
        }
    }

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
        isRemoveState.value = userIdToRemove.isNotEmpty()
    }

    fun removeUsers() {

    }

    fun isItemSelected(id: Int) : Boolean {
        return userIdToRemove.contains(id)
    }

}