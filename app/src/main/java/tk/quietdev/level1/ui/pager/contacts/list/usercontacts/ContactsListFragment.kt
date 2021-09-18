package tk.quietdev.level1.ui.pager.contacts.list.usercontacts

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import tk.quietdev.level1.R
import tk.quietdev.level1.databinding.ListItemBinding
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.ui.pager.contacts.list.BaseListFragment
import tk.quietdev.level1.ui.pager.contacts.list.adapter.ContactHolder
import tk.quietdev.level1.ui.pager.contacts.list.adapter.ContactHolderBase
import tk.quietdev.level1.ui.pager.contacts.list.adapter.ContactsAdapter
import tk.quietdev.level1.utils.Const
import tk.quietdev.level1.utils.ListState

@AndroidEntryPoint
class ContactsListFragment : BaseListFragment() {

    private val viewModel: ContactListViewModel by viewModels()




  /*  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addListeners()
    }*/

    override fun initObservables() {
        viewModel.apply {
            listState.observe(viewLifecycleOwner) { listState ->
                when (listState) {
                    ListState.MULTISELECT -> {
                        binding.btnAdd.text = getString(R.string.remove)
                    }
                    ListState.NORMAL -> {
                        binding.btnAdd.text = getString(R.string.add_contact)
                    }
                }
                userList.observe(viewLifecycleOwner) {

                    val list = it.data
                    Log.d("TAG", "initObservables: ${it.data?.size}")
                    list?.let { userList ->
                        contactsAdapter.submitList(userList)
                    }
                }
            }
        }
    }

    // works

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

   /* override fun getContactAdapter() = ContactsAdapter(
        onItemClickListener,
        viewModel.listState,
        this,
        ContactHolder.HolderType.REMOVE
    )*/

    private fun removeContact(userModel: UserModel, position: Int) {
        viewModel.removeContact(userModel, position)
        showDeletionUndoSnackBar(userModel.id)
    }

    private fun addListeners() {
        binding.btnAdd.setOnClickListener {
            fabClicked()
        }
    }

    private fun fabClicked() {
        when (viewModel.listState.value) {
            ListState.MULTISELECT -> {
                // TODO: 9/18/2021 remove all
            }
            ListState.NORMAL -> {
                findNavController().navigate(
                    ContactsListFragmentDirections
                        .actionContactsListFragmentToAddContactsListFragment()
                )
            }
        }
    }


    private fun showDeletionUndoSnackBar(id: Int) {
        Snackbar.make(
            binding.root,
            getString(R.string.contact_removed),
            LENGTH_INDEFINITE
        )
            .setTextColor(Color.WHITE)
            .setAction(getString(R.string.add_back)) {
                viewModel.addUserBack(id)
            }
            .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    super.onDismissed(transientBottomBar, event)
                }

                override fun onShown(transientBottomBar: Snackbar?) {
                    super.onShown(transientBottomBar)
                    Handler(Looper.getMainLooper()).postDelayed({
                        transientBottomBar?.dismiss()
                    }, Const.TIME_5_SEC)
                }
            })
            .show()
    }

    private fun openContactDetail(userModel: UserModel) {
        findNavController().navigate(
            ContactsListFragmentDirections.actionContactsListFragmentToContactDetailFragment(
                userModel
            )
        )
    }


    private val onItemClickListener = object : ContactHolderBase.OnItemClickListener {

        override fun onItemClick(userModel: UserModel) {
            if (viewModel.listState.value == ListState.MULTISELECT) {
                viewModel.toggleUserSelected(userModel.id)
            } else {
                openContactDetail(userModel)
            }
        }

        override fun onLongItemClick(userModel: UserModel): Boolean {
            viewModel.toggleUserSelected(userModel.id)
            return true
        }

        override fun onIconClick(userModel: UserModel, position: Int) =
            removeContact(userModel, position)
    }

    override fun isItemSelected(id: Int): Boolean {
        return viewModel.isItemSelected(id)
    }
}