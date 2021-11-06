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
import tk.quietdev.level1.domain.models.UserModel
import tk.quietdev.level1.usecase.FlowNotUserContactsUseCase
import tk.quietdev.level1.usecase.FlowUserContactsUseCase
import kotlin.reflect.KSuspendFunction0


class ParentPagerViewModel @AssistedInject constructor(
    private val flowUserContactsUseCase: FlowUserContactsUseCase,
    private val flowNotUserContactsUseCase: FlowNotUserContactsUseCase,
    @Assisted selectedUserId: Int,
    @Assisted type: ViewPagerType
) : ViewModel() {

    var userListAll: MutableLiveData<List<UserModel>> = MutableLiveData(listOf())
    var currentPage: Int = -1

    init {
        when (type) {
            ViewPagerType.ALL_USERS -> initList(flowNotUserContactsUseCase::invoke, selectedUserId)
            ViewPagerType.CONTACTS -> initList(flowUserContactsUseCase::invoke, selectedUserId)
        }
    }

    private fun initList(
        func: KSuspendFunction0<Flow<Resource<List<UserModel>>>>,
        selectedUserId: Int
    ) {
        viewModelScope.launch {
            func.invoke().filter {
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
        private val type: ViewPagerType,

        ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return assistedFactory.create(
                userId,
                type,
            ) as T
        }
    }
}

@AssistedFactory
interface ParentPagerViewModelFactory {
    fun create(
        selectedUserId: Int,
        type: ViewPagerType,
    ): ParentPagerViewModel
}