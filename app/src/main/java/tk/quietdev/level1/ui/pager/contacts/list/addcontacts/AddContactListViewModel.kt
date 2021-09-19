package tk.quietdev.level1.ui.pager.contacts.list.addcontacts


import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import tk.quietdev.level1.data.repository.Repository
import tk.quietdev.level1.ui.pager.contacts.list.BaseListViewModel
import javax.inject.Inject

@HiltViewModel
class AddContactListViewModel @Inject constructor(
    repository: Repository
) : BaseListViewModel(repository) {

    override var search = ""

    init {
        repository.getAllUsersFlow().onEach {
            userListAll = it
            userList.value = searchQueryMap(userListAll)
        }.launchIn(viewModelScope)
    }


}



