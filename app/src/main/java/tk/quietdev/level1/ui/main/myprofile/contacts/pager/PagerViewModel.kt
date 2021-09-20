package tk.quietdev.level1.ui.main.myprofile.contacts.pager

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import tk.quietdev.level1.data.repository.RemoteApiRepository
import tk.quietdev.level1.data.repository.Repository
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.utils.Resource
import javax.inject.Inject

@HiltViewModel
class PagerViewModel @Inject constructor(
    repository: Repository
) : ViewModel() {

    var userListAll: MutableLiveData<List<UserModel>> = MutableLiveData(listOf())
    var job : Job? = null

    init {
       job = (repository as RemoteApiRepository).getCurrentUserContactIdsFlow().onEach {
           when (it) {
               is Resource.Success -> {
                   it.data?.let {
                       userListAll.value = repository.getCurrentUserContactsFlow(it).first().data
                       job?.cancel()
                   }
               }
           }
        }.launchIn(viewModelScope)
    }

}