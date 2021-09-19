package tk.quietdev.level1.ui.pager.contacts.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.repository.Repository
import tk.quietdev.level1.ui.pager.contacts.list.adapter.HolderState
import tk.quietdev.level1.utils.Resource



abstract class BaseListViewModel (
    protected val repository: Repository
) : ViewModel() {
    val userList = MutableLiveData<Resource<List<UserModel>>>()
    val holderState = MutableLiveData(mutableMapOf<Int, HolderState>())
    var addJob: Job? = null

    fun action(action: (UserModel) -> Flow<Resource<List<UserModel>>>, userModel: UserModel) {


        addJob =  action(userModel).onEach {
            when (it) {
                is Resource.Success -> {
                    holderState.value?.set(userModel.id, HolderState.ADDED)
                    addJob?.cancel()
                }
                is Resource.Error -> {
                    holderState.value?.set(userModel.id, HolderState.FAIL)
                    addJob?.cancel()
                }
                is Resource.Loading -> {
                    holderState.value?.set(userModel.id, HolderState.PENDING)
                }
            }
            holderState.postValue(holderState.value)
        }.launchIn(viewModelScope)
    }

    fun test() {

    }


}