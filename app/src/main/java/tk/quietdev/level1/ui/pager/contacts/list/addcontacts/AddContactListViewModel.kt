package tk.quietdev.level1.ui.pager.contacts.list.addcontacts


import androidx.fragment.app.activityViewModels
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.repository.Repository
import tk.quietdev.level1.ui.pager.AppbarSharedViewModel
import tk.quietdev.level1.utils.ListState
import tk.quietdev.level1.utils.Resource
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddContactListViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var search = ""

    var userList = repository.getAllUsersFlow().asLiveData().map {
        when (it) {
            is Resource.Error -> it
            is Resource.Success -> Resource.Success((it.data?.filter { it.email.startsWith(search) }))
            is Resource.Loading -> Resource.Loading((it.data?.filter { it.email.startsWith(search) }))
        }
    }

    private var addedContacts = TreeSet<Int>()
    var listState = MutableLiveData(ListState.NORMAL)

    fun isItemAdded(id: Int): Boolean {
        return addedContacts.contains(id)
    }

    fun addUserContact(userModel: UserModel) {
        viewModelScope.launch {
            addedContacts.add(userModel.id)
            repository.addUserContact(userModel)
        }
    }


}

