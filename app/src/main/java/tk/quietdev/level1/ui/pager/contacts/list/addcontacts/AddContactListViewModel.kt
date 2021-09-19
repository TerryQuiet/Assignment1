package tk.quietdev.level1.ui.pager.contacts.list.addcontacts


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.repository.Repository
import tk.quietdev.level1.ui.pager.contacts.list.BaseListViewModel
import tk.quietdev.level1.utils.ListState
import tk.quietdev.level1.utils.Resource
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddContactListViewModel @Inject constructor(
    repository: Repository
) : BaseListViewModel(repository) {

    private var search = ""
    private var addedContacts = TreeSet<Int>()
    var listState = MutableLiveData(ListState.NORMAL)


    private lateinit var userListAll: Resource<List<UserModel>>

    private fun searchQueryMap(it: Resource<List<UserModel>>): Resource<List<UserModel>> =
        when (it) {
            is Resource.Error -> it
            is Resource.Success -> Resource.Success((it.data?.filter { it.email.startsWith(search) }
                ?: listOf()))
            is Resource.Loading -> Resource.Loading((it.data?.filter { it.email.startsWith(search) }
                ?: listOf()))
        }

    init {
        repository.getAllUsersFlow().onEach {
            userListAll = it
            userList.value = searchQueryMap(userListAll)
        }.launchIn(viewModelScope)
    }

    fun changeSearchQuery(text: String) {
        search = text
        userList.value?.let {
            userList.value = searchQueryMap(userListAll)
        }
    }

    fun addUserContact(userModel: UserModel) {
        addedContacts.add(userModel.id)
        action(repository::addUserContact, userModel)

    }




}



