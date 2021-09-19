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
import tk.quietdev.level1.ui.pager.contacts.list.BaseListViewModel
import tk.quietdev.level1.utils.ListState
import tk.quietdev.level1.utils.Resource
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RemoveContactListViewModel @Inject constructor(
    repository: Repository
) : BaseListViewModel(repository) {



    private var deletedUserPosition: Int? = null


    var listState = MutableLiveData<List<Int>>(listOf())

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
        listState.value = listState.value?.minus(listOf(userModel.id))
        repository.removeUserContact(userModel).launchIn(viewModelScope) // todo cancel after result?
        action(repository::removeUserContact, userModel)
    }

    fun toggleUserSelected(userId: Int) {
        if (listState.value?.contains(userId) == true) {
            listState.value = listState.value?.minus(userId)
        } else {
            listState.value = listState.value?.plus(userId)
        }

    }

    fun isItemSelected(id: Int): Boolean {
        return listState.value?.contains(id) == true
    }


}
