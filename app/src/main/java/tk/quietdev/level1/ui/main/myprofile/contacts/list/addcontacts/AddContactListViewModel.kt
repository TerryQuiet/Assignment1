package tk.quietdev.level1.ui.main.myprofile.contacts.list.addcontacts


import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import tk.quietdev.level1.ui.main.myprofile.contacts.list.ParentListViewModel
import tk.quietdev.level1.usecase.AddUserContactUseCase
import tk.quietdev.level1.usecase.FlowNotUserContactsUseCase
import tk.quietdev.level1.usecase.RefreshAllUsersUseCase
import javax.inject.Inject

@HiltViewModel
class AddContactListViewModel @Inject constructor(
    useCase: AddUserContactUseCase,
    private val refreshUseCase: RefreshAllUsersUseCase,
    private val flowUsersUseCase: FlowNotUserContactsUseCase
) : ParentListViewModel(useCase) {

    override var search = ""

    init {
        viewModelScope.launch {
            refreshUseCase.invoke()
            flowUsersUseCase.invoke().onEach {
                userListAll = it
                userListToShow.value = searchQueryMap(userListAll)
            }.launchIn(viewModelScope)
        }
    }


}



