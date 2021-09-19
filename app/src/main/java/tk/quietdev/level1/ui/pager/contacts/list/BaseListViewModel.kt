package tk.quietdev.level1.ui.pager.contacts.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import tk.quietdev.level1.data.repository.Repository
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.ui.pager.contacts.list.adapter.HolderState
import tk.quietdev.level1.utils.Resource


abstract class BaseListViewModel(
    protected val repository: Repository
) : ViewModel() {
    val userList = MutableLiveData<Resource<List<UserModel>>>()
    val holderState = MutableLiveData(mutableMapOf<Int, HolderState>())
    val actionJobs = mutableMapOf<Int, Job>()

    abstract var search: String


    protected lateinit var userListAll: Resource<List<UserModel>>

    fun action(
        action: (Int) -> Flow<Resource<List<UserModel>>>,
        id: Int,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    ) {
        val job = action(id).onEach {
            when (it) {
                is Resource.Success -> {
                    onSuccess()
                    actionJobs.remove(id)?.cancel()
                }
                is Resource.Error -> {
                    holderState.value?.set(id, HolderState.FAIL)
                    onFail()
                    actionJobs.remove(id)?.cancel()
                }
                is Resource.Loading -> {
                    holderState.value?.set(id, HolderState.PENDING)
                }
            }
            holderState.postValue(holderState.value)
        }.launchIn(viewModelScope)
        actionJobs[id] = job
    }

    fun changeSearchQuery(text: String) {
        search = text
        userList.value?.let {
            userList.value = searchQueryMap(userListAll)
        }
    }

    fun addUserContact(id: Int) {
        action(
            repository::addUserContact,
            id,
            onSuccess = { holderState.value?.set(id, HolderState.SUCCESS) },
            onFail = {}
        )
    }


    protected fun searchQueryMap(it: Resource<List<UserModel>>): Resource<List<UserModel>> =
        when (it) {
            is Resource.Error -> it
            is Resource.Success -> Resource.Success((it.data?.filter {
                it.email.startsWith(
                    search,
                    true
                )
            }
                ?: listOf()))
            is Resource.Loading -> Resource.Loading((it.data?.filter {
                it.email.startsWith(
                    search,
                    true
                )
            }
                ?: listOf()))
        }

}