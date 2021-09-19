package tk.quietdev.level1.ui.pager.contacts.list.addcontacts


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.ui.pager.contacts.list.BaseListFragment
import tk.quietdev.level1.ui.pager.contacts.list.adapter.AddContactsAdapter
import tk.quietdev.level1.ui.pager.contacts.list.adapter.holders.ContactHolderBase

@AndroidEntryPoint
class AddContactsListFragment : BaseListFragment() {

    override val viewModel: AddContactListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAdd.visibility = View.GONE
    }

    override fun getContactAdapter() = AddContactsAdapter(
        onClickListener = onItemClickListener,
        holderState = viewModel.holderState
    )

    private fun addUser(userModel: UserModel) {
        viewModel.addUserContact(userModel.id)
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
            openContactDetail(userModel)
        }

        override fun onLongItemClick(userModel: UserModel): Boolean {
            return true
        }

        override fun onIconClick(userModel: UserModel, position: Int) = addUser(userModel)

    }


}