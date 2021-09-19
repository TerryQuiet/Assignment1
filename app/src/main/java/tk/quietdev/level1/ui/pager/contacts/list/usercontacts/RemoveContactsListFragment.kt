package tk.quietdev.level1.ui.pager.contacts.list.usercontacts

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import tk.quietdev.level1.R
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.ui.pager.contacts.list.BaseListFragment
import tk.quietdev.level1.ui.pager.contacts.list.adapter.holders.ContactHolderBase
import tk.quietdev.level1.ui.pager.contacts.list.adapter.RemoveContactsAdapter
import tk.quietdev.level1.ui.pager.contacts.list.adapter.HolderState
import tk.quietdev.level1.utils.Const

@AndroidEntryPoint
class RemoveContactsListFragment : BaseListFragment() {

    private val viewModelRemove: RemoveContactListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addListeners()
    }

    override fun initObservables() {
        viewModelRemove.apply {
            listState.observe(viewLifecycleOwner) { listState ->
                when (listState.isEmpty()) {
                    false -> {
                        binding.btnAdd.text = getString(R.string.remove)
                    }
                    true -> {
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



    override fun getContactAdapter() = RemoveContactsAdapter(
        onClickListener = onItemClickListener,
        removeState = viewModelRemove.listState,
        holderState = viewModelRemove.holderState
    )


    private fun removeContact(userModel: UserModel, position: Int) {
        viewModelRemove.removeContact(userModel, position)

        showDeletionUndoSnackBar(userModel.id)
    }

    private fun addListeners() {
        binding.btnAdd.setOnClickListener {
            fabClicked()
        }
    }

    private fun fabClicked() {
        when (viewModelRemove.listState.value?.isEmpty()) {
            false -> {
                // TODO: 9/18/2021 remove all
            }
            true -> {
                findNavController().navigate(
                    RemoveContactsListFragmentDirections
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
                viewModelRemove.addUserBack(id)
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
            RemoveContactsListFragmentDirections.actionContactsListFragmentToContactDetailFragment(
                userModel
            )
        )
    }

    // we need to remove whoever have added state.
    override fun onResume() {
        super.onResume()
        viewModelRemove.holderState.value = viewModelRemove.holderState.value?.filterValues { HolderState.ADDED != it }?.toMutableMap()
    }


    private val onItemClickListener = object : ContactHolderBase.OnItemClickListener {

        override fun onItemClick(userModel: UserModel) {
            if (!viewModelRemove.listState.value.isNullOrEmpty()) {
                viewModelRemove.toggleUserSelected(userModel.id)
            } else {
                openContactDetail(userModel)
            }
        }

        override fun onLongItemClick(userModel: UserModel): Boolean {
            viewModelRemove.toggleUserSelected(userModel.id)
            return true
        }

        override fun onIconClick(userModel: UserModel, position: Int) =
            removeContact(userModel, position)
    }

}