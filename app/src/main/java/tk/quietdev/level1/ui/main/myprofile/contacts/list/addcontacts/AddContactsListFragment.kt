package tk.quietdev.level1.ui.main.myprofile.contacts.list.addcontacts


import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import tk.quietdev.level1.R
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.ui.main.myprofile.contacts.list.ListFragmentParent
import tk.quietdev.level1.ui.main.myprofile.contacts.list.adapter.AddContactsAdapter
import tk.quietdev.level1.ui.main.myprofile.contacts.list.adapter.holders.ContactHolderParent
import tk.quietdev.level1.ui.main.myprofile.contacts.pager.ViewPagerType

@AndroidEntryPoint
class AddContactsListFragment : ListFragmentParent() {

    override val viewModel: AddContactListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAdd.visibility = View.GONE
    }

    override fun getContactAdapter() = AddContactsAdapter(
        getDrawable(requireContext(), R.drawable.ic_add),
        onClickListener = onItemClickListener,
        holderState = viewModel.holderState
    )

    private fun addUser(userModel: UserModel) {
        viewModel.addUserContact(userModel.id)
    }

    private fun openContactDetail(userModel: UserModel) {
        findNavController().navigate(
            AddContactsListFragmentDirections.actionAddContactsListFragmentToViewPagerContainer(
                userModel.id,
                ViewPagerType.ALL_USERS
            )
        )
    }

    private val onItemClickListener = object : ContactHolderParent.OnItemClickListener {

        override fun onItemClick(userModel: UserModel) {
            openContactDetail(userModel)
        }

        override fun onLongItemClick(userModel: UserModel): Boolean {
            return true
        }

        override fun onIconClick(userModel: UserModel, position: Int) = addUser(userModel)

    }


}