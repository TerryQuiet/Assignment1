package tk.quietdev.level1.ui.main.myprofile.contacts.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import tk.quietdev.level1.common.Resource
import tk.quietdev.level1.domain.models.UserModel
import tk.quietdev.level1.ui.main.myprofile.contacts.list.adapter.holders.HolderState
import tk.quietdev.level1.usecase.AddUserContactUseCase
import kotlin.reflect.KSuspendFunction1

abstract class ParentListViewModel(
    private val addUserContactUseCase: AddUserContactUseCase
) : ViewModel() {
    val userListToShow = MutableLiveData<Resource<List<UserModel>>>()
    val holderState = MutableLiveData(mutableMapOf<Int, HolderState>())
    private val actionJobs = mutableMapOf<Int, Job>()

    abstract var search: String
    protected lateinit var userListAll: Resource<List<UserModel>>


    fun action(
        action: KSuspendFunction1<Int, Flow<Resource<Boolean>>>,
        id: Int,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    ) {
        viewModelScope.launch {
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

    }

    fun changeSearchQuery(text: String) {
        search = text
        userListToShow.value?.let {
            userListToShow.value = searchQueryMap(userListAll)
        }
    }

    fun addUserContact(id: Int) {
        action(
            addUserContactUseCase::invoke,
            id,
            onSuccess = { holderState.value?.set(id, HolderState.SUCCESS) },
            onFail = {}
        )
    }

    protected fun searchQueryMap(it: Resource<List<UserModel>>): Resource<List<UserModel>> =
        when (it) {
            is Resource.Error -> it
            is Resource.Success -> Resource.Success((it.data?.filter {
                it.email.contains(
                    search,
                    true
                )
            } ?: listOf()))

            is Resource.Loading -> Resource.Loading((it.data?.filter {
                it.email.contains(
                    search,
                    true
                )
            } ?: listOf()))
        }

}