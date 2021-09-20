package tk.quietdev.level1.ui.main.myprofile.contacts.list.removecontacts


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import tk.quietdev.level1.data.repository.RemoteApiRepository
import tk.quietdev.level1.data.repository.Repository
import tk.quietdev.level1.ui.main.myprofile.contacts.list.BaseListViewModel
import javax.inject.Inject

@HiltViewModel
class RemoveContactListViewModel @Inject constructor(
    repository: Repository
) : BaseListViewModel(repository) {

    override var search = ""

    var contactsToRemove = MutableLiveData<List<Int>>(listOf())

    fun addUserBack(id: Int) {
        addUserContact(id)
    }

    init {
        (repository as RemoteApiRepository).getCurrentUserContactIdsFlow().onEach {
            it.data?.let {
                userListAll = repository.getCurrentUserContactsFlow(it).first()
                userListToShow.value = searchQueryMap(userListAll)
            }
        }.launchIn(viewModelScope)
    }

    fun removeContact(id: Int, position: Int? = null) {
        action(repository::removeUserContact, id,
            onSuccess = {
                holderState.value = holderState.value?.minus(id)?.toMutableMap()
                contactsToRemove.value = contactsToRemove.value?.minus(listOf(id))
            },
            onFail = {})
    }

    fun toggleUserSelected(userId: Int) {
        if (contactsToRemove.value?.contains(userId) == true) {
            contactsToRemove.value = contactsToRemove.value?.minus(userId)
        } else {
            contactsToRemove.value = contactsToRemove.value?.plus(userId)
        }
    }

    fun removeAll() {
        val toRemove = contactsToRemove.value
        toRemove?.forEach {
            removeContact(it)
        }
    }

}
