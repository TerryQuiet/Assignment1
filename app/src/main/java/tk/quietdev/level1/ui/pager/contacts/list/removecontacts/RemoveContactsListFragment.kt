package tk.quietdev.level1.ui.pager.contacts.list.removecontacts

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import tk.quietdev.level1.R
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.ui.pager.ViewPagerContainerFragmentDirections
import tk.quietdev.level1.ui.pager.contacts.list.BaseListFragment
import tk.quietdev.level1.ui.pager.contacts.list.adapter.HolderState
import tk.quietdev.level1.ui.pager.contacts.list.adapter.RemoveContactsAdapter
import tk.quietdev.level1.ui.pager.contacts.list.adapter.holders.ContactHolderBase
import tk.quietdev.level1.utils.Const

@AndroidEntryPoint
class RemoveContactsListFragment : BaseListFragment() {

    override val viewModel: RemoveContactListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addListeners()
    }

    override fun initObservables() {
        super.initObservables()
        viewModel.apply {
            contactsToRemove.observe(viewLifecycleOwner) { listState ->
                when (listState.isEmpty()) {
                    false -> {
                        binding.btnAdd.text = getString(R.string.remove)
                    }
                    true -> {
                        binding.btnAdd.text = getString(R.string.add_contact)
                    }

                }
            }
        }
    }


    override fun getContactAdapter() = RemoveContactsAdapter(
        onClickListener = onItemClickListener,
        removeState = viewModel.contactsToRemove,
        holderState = viewModel.holderState
    )


    private fun removeContact(userModel: UserModel, position: Int) {
        viewModel.removeContact(userModel.id, position)
        showDeletionUndoSnackBar(userModel.id)
    }

    private fun addListeners() {
        binding.btnAdd.setOnClickListener {
            fabClicked()
        }
    }

    private fun fabClicked() {
        when (viewModel.contactsToRemove.value?.isEmpty()) {
            false -> {
                viewModel.removeAll()
            }
            true -> {
                findNavController().navigate(
                    ViewPagerContainerFragmentDirections
                        .actionPagerDestToAddContactsListFragment()
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
            ViewPagerContainerFragmentDirections.actionPagerDestToContactDetailFragment(
                userModel
            )
        )
    }

    // we need to remove whoever have added state.
    override fun onResume() {
        super.onResume()
        viewModel.holderState.value =
            viewModel.holderState.value?.filterValues { HolderState.SUCCESS != it }?.toMutableMap()
    }


    private val onItemClickListener = object : ContactHolderBase.OnItemClickListener {

        override fun onItemClick(userModel: UserModel) {
            if (!viewModel.contactsToRemove.value.isNullOrEmpty()) {
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

}