package tk.quietdev.level1.ui.main.myprofile.contacts.list.removecontacts


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import tk.quietdev.level1.ui.main.myprofile.contacts.list.ParentListViewModel
import tk.quietdev.level1.usecase.AddUserContactUseCase
import tk.quietdev.level1.usecase.FlowUserContactsUseCase
import tk.quietdev.level1.usecase.RefreshUserContactsUseCase
import tk.quietdev.level1.usecase.RemoveUserContactUseCase
import javax.inject.Inject

@HiltViewModel
class RemoveContactListViewModel @Inject constructor(
    addUserContactUseCase: AddUserContactUseCase,
    private val removeUserContactUseCase: RemoveUserContactUseCase,
    private val refreshUserContactsUseCase: RefreshUserContactsUseCase,
    private val flowUserContactsUseCase: FlowUserContactsUseCase
) : ParentListViewModel(addUserContactUseCase) {

    override var search = ""

    var contactsToRemove = MutableLiveData<List<Int>>(listOf())

    fun addUserBack(id: Int) {
        addUserContact(id)
    }

    init {
        viewModelScope.launch {
            flowUserContactsUseCase.invoke().onEach { userList ->
                userListAll = userList
                userListToShow.value = searchQueryMap(userListAll)
            }.launchIn(viewModelScope)
            refreshUserContactsUseCase.invoke()
        }
    }

    fun removeContact(id: Int, position: Int? = null) {
        action(removeUserContactUseCase::invoke, id,
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
