package tk.quietdev.level1.ui.main.myprofile.contacts.pager

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import tk.quietdev.level1.common.Resource
import tk.quietdev.level1.data.Repository
import tk.quietdev.level1.domain.models.UserModel

class ParentPagerViewModel @AssistedInject constructor(
    val repository: Repository,
    @Assisted selectedUserId: Int,
    @Assisted type: ViewPagerType
) : ViewModel() {

    var userListAll: MutableLiveData<List<UserModel>> = MutableLiveData(listOf())
    var currentPage: Int = -1

    init {
        when (type) {
            ViewPagerType.ALL_USERS -> initAllUsers(selectedUserId)
            ViewPagerType.CONTACTS -> initCurrentUserContacts(selectedUserId)
        }
    }

    private fun initCurrentUserContacts(selectedUserId: Int) {
        viewModelScope.launch {
            repository.getCurrentUserContactIdsFlow(false).filter {
                it is Resource.Success
            }.collect {
                it.data?.let {
                    val list =
                        repository.getCurrentUserContactsFlow(it, false).first().data ?: listOf()
                    initPage(selectedUserId, list)
                    userListAll.value = list
                }
            }
        }
    }

    private fun initAllUsers(selectedUserId: Int) {
        viewModelScope.launch {
            repository.getAllUsersFlow().filter {
                it is Resource.Success
            }.collect {
                it.data?.let {
                    initPage(selectedUserId, it)
                    userListAll.value = it
                }
            }
        }
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
        private val userId: Int,
        private val type: ViewPagerType
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return assistedFactory.create(userId, type) as T
        }
    }
}

@AssistedFactory
interface ParentPagerViewModelFactory {
    fun create(selectedUserId: Int, type: ViewPagerType): ParentPagerViewModel
}