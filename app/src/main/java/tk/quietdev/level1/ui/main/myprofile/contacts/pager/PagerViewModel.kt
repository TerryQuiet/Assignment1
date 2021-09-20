package tk.quietdev.level1.ui.main.myprofile.contacts.pager

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import tk.quietdev.level1.data.repository.Repository
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.utils.Resource
import javax.inject.Inject

@HiltViewModel
class PagerViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {

    var userListAll: MutableLiveData<List<UserModel>> = MutableLiveData(listOf())

    private var job: Job? = null
    var currentPage: Int = -1

    fun initCurrentUserContacts(selectedUserId: Int) {
        job = repository.getCurrentUserContactIdsFlow().onEach {
            if (it is Resource.Success) {
                it.data?.let {
                    val list= repository.getCurrentUserContactsFlow(it).first().data ?: listOf()
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

}