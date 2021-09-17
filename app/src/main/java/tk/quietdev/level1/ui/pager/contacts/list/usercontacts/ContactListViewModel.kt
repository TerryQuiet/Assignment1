package tk.quietdev.level1.ui.pager.contacts.list.usercontacts


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.repository.RemoteApiRepository
import tk.quietdev.level1.repository.Repository
import tk.quietdev.level1.utils.ListState
import tk.quietdev.level1.utils.Resource
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ContactListViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var userList = MutableLiveData<Resource<List<UserModel>>>()

    private var deletedUserPosition: Int? = null
    private var multiSelectList = TreeSet<Int>()

    var listState = MutableLiveData(ListState.NORMAL)

    fun addUserBack(id: Int) {

    }

    init {
            (repository as RemoteApiRepository).getCurrentUserContactIdsFlow().onEach {
                Log.d("TAG", "$it: ")
                it.data?.let {
                    userList.value = repository.getCurrentUserContactsFlow(it).first()
                }
            }.launchIn(viewModelScope)
    }

    fun removeContact(userModel: UserModel, position: Int) {
        repository.removeUserContact(userModel).launchIn(viewModelScope) // todo cancel after result?
    }

    fun toggleUserSelected(userId: Int) {
        val isUserRemovedFromList = multiSelectList.remove(userId)
        if (!isUserRemovedFromList) {
            multiSelectList.add(userId)
        }
        listState.value =
            if (multiSelectList.isNotEmpty()) ListState.MULTISELECT else ListState.NORMAL
    }

    fun isItemSelected(id: Int): Boolean {
        return multiSelectList.contains(id)
    }


}
