package tk.quietdev.level1.ui.main.myprofile.contacts.list.addcontacts


import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import tk.quietdev.level1.data.Repository
import tk.quietdev.level1.ui.main.myprofile.contacts.list.ParentListViewModel
import javax.inject.Inject

@HiltViewModel
class AddContactListViewModel @Inject constructor(
    repository: Repository
) : ParentListViewModel(repository) {

    override var search = ""

    init {
        repository.getAllUsersFlow().onEach {
            userListAll = it
            userListToShow.value = searchQueryMap(userListAll)
        }.launchIn(viewModelScope)
    }


}



