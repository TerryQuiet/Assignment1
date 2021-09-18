package tk.quietdev.level1.ui.pager.contacts.list.addcontacts

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.ui.pager.contacts.list.BaseListFragment
import tk.quietdev.level1.ui.pager.contacts.list.adapter.ContactHolder
import tk.quietdev.level1.ui.pager.contacts.list.adapter.ContactHolderBase
import tk.quietdev.level1.ui.pager.contacts.list.adapter.ContactsAdapter
import tk.quietdev.level1.utils.ListState

@AndroidEntryPoint
class AddContactsListFragment : BaseListFragment() {

    private val viewModel: AddContactListViewModel by viewModels()

    override fun initObservables() {
        viewModel.apply {
            listState.observe(viewLifecycleOwner) { state ->
                when (state) {
                    ListState.NORMAL -> {
                        binding.btnAdd.visibility = View.GONE
                    }
                }
                userList.observe(viewLifecycleOwner) {
                    val list = it.data
                    list?.let { userList ->
                        contactsAdapter.submitList(userList)
                    }
                }
            }
        }

        appbarSharedViewModel.searchText.observe(viewLifecycleOwner) {
            viewModel.changeSearchQuery(it)
        }

    }

    // works

    /*override fun getContactAdapter() = ContactsAdapter(
        onItemClickListener,
        viewModel.listState,
        this,
        ContactHolderBase.HolderType.ADD
    )*/

    override fun getContactAdapter(): ContactsAdapter<ContactHolderBase> = ContactsAdapter(
        holder = ContactHolder(
            null,
            holderType = ContactHolderBase.HolderType.REMOVE,
            itemStateChecker = this,
            onClickListener = onItemClickListener
        ),
        removeState =  viewModel.listState,
        holderType = ContactHolderBase.HolderType.REMOVE

    )

    private fun addUser(userModel: UserModel) {
        viewModel.addUserContact(userModel)
    }


    private fun openContactDetail(userModel: UserModel) {
        findNavController().navigate(
            AddContactsListFragmentDirections.actionAddContactsListFragmentToContactDetailFragment(
                userModel
            )
        )
    }

    private val onItemClickListener = object : ContactHolderBase.OnItemClickListener {

        override fun onItemClick(userModel: UserModel) {
            if (viewModel.listState.value != ListState.MULTISELECT) {
                openContactDetail(userModel)
            }
        }

        override fun onLongItemClick(userModel: UserModel): Boolean {
            return true
        }

        override fun onIconClick(userModel: UserModel, position: Int) = addUser(userModel)

    }

    override fun isItemAdded(id: Int): Boolean {
        return viewModel.isItemAdded(id)
    }

}