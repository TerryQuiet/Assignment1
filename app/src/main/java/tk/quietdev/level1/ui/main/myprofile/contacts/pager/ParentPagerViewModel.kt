package tk.quietdev.level1.ui.main.myprofile.contacts.pager

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import tk.quietdev.level1.data.repository.Repository
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.utils.Resource

class ParentPagerViewModel @AssistedInject constructor(
    val repository: Repository,
    @Assisted string: String
) : ViewModel() {

    var userListAll: MutableLiveData<List<UserModel>> = MutableLiveData(listOf())

    private var job: Job? = null
    var currentPage: Int = -1

    fun initCurrentUserContacts(selectedUserId: Int) {
        job = repository.getCurrentUserContactIdsFlow(false).onEach {
            Log.d("TAG", "initCurrentUserContacts: ${it.message}")
            if (it is Resource.Success) {
                it.data?.let {
                    val list= repository.getCurrentUserContactsFlow(it, false).first().data ?: listOf()
                    initPage(selectedUserId, list)
                    userListAll.value = list
                    job?.cancel()
                }
            }
        }.launchIn(viewModelScope)
    }

    fun initAllUsers(selectedUserId: Int) {
        job = repository.getAllUsersFlow().onEach {
            if (it is Resource.Success) {
                it.data?.let {
                    initPage(selectedUserId, it)
                    userListAll.value = it
                    job?.cancel()
                }
            }
        }.launchIn(viewModelScope)
    }


    private fun initPage(selectedUserId: Int, list: List<UserModel>) {
        if (currentPage == -1) {
            list.forEachIndexed { index, userModel ->
                if (userModel.id == selectedUserId) {
                    currentPage = index
                    return@forEachIndexed
                }
            }
        }
    }

    class Factory(
        private val assistedFactory: ParentPagerViewModelFactory,
        private val name: String,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return assistedFactory.create(name) as T
        }
    }

}

@AssistedFactory
interface ParentPagerViewModelFactory {
    fun create(name: String): ParentPagerViewModel
}