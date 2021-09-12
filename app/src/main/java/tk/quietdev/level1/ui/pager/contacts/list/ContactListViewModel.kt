package tk.quietdev.level1.ui.pager.contacts.list


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    var userList = MutableLiveData<MutableList<UserModel>>()
    private var deletedUserPosition: Int? = null
    private var  userIdToRemove = TreeSet<Int>()

    var isRemoveState = MutableLiveData(false)

    init {
        viewModelScope.launch {
            repository.cacheCurrentUserContactsFromApi()
        }
    }

    fun addUserBack(id: Int) {
        val user = repository.getUserWithNoValidation(id)
        user?.let {
            deletedUserPosition?.let {
                userList.value?.add(it, user)
                deletedUserPosition = null
                updateLiveData()
            }
        }
    }

    fun removeUser(userModel: UserModel, position: Int) {
        val removedUser = userList.value?.remove(userModel)
        removedUser?.let {
            deletedUserPosition = position
            updateLiveData()
            userIdToRemove.remove(userModel.id)
            isRemoveState.value = userIdToRemove.isNotEmpty()
        }
    }

    fun addNewUser(userModel: UserModel) {
        userList.value?.add(userModel)
        updateLiveData()
    }

    private fun updateLiveData() {
        userList.value = userList.value
    }

    fun toggleUserSelected(userId: Int) {
        val isUserRemovedFromList = userIdToRemove.remove(userId)
        if (!isUserRemovedFromList) {
            userIdToRemove.add(userId)
        }
        isRemoveState.value = userIdToRemove.isNotEmpty()
    }

    fun removeUsers() {
        val newList = userList.value?.filter { !userIdToRemove.contains(it.id) }?.toMutableList()
        newList?.let {
            userList.value = it
        }
        userIdToRemove.clear()
        isRemoveState.value = false
    }

    fun isItemSelected(id: Int) : Boolean {
        return userIdToRemove.contains(id)
    }

}