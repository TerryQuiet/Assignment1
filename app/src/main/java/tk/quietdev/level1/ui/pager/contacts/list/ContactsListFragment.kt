package tk.quietdev.level1.ui.pager.contacts.list

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import tk.quietdev.level1.R
import tk.quietdev.level1.databinding.FragmentContactsBinding
import tk.quietdev.level1.models.User
import tk.quietdev.level1.ui.pager.contacts.ContactsSharedViewModel
import tk.quietdev.level1.ui.pager.contacts.adapter.ContactHolder
import tk.quietdev.level1.ui.pager.contacts.adapter.ContactsAdapter
import tk.quietdev.level1.ui.pager.contacts.dialog.AddContactDialog
import tk.quietdev.level1.utils.Const


class ContactsListFragment : Fragment() {

    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ContactListViewModel by viewModel()
    private val contactsSharedViewModel: ContactsSharedViewModel by sharedViewModel()
    private val contactsAdapter: ContactsAdapter by lazy { getContactAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservables()
        initRecycleView()
        addListeners()
    }

    private fun initRecycleView() {
        binding.recycleView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = contactsAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

    private fun initObservables() {
        viewModel.apply {
            userList.observe(viewLifecycleOwner) { newList ->
                contactsAdapter.submitList(newList.toList())
            }
            isRemoveState.observe(viewLifecycleOwner) { isRemoveState ->
                if (isRemoveState) {
                    binding.btnAdd.text = getString(R.string.remove)
                } else {
                    binding.btnAdd.text = getString(R.string.add_contact)
                }

            }
        }
        contactsSharedViewModel.apply {
            newUser.observe(viewLifecycleOwner) { newUser ->
                newUser?.let {
                    viewModel.addNewUser(newUser)
                    contactsSharedViewModel.newUser.value = null
                }
            }
            updatedUser.observe(viewLifecycleOwner) {
                if (it != null) {
                    viewModel.updateUser(it)
                    updatedUser.value = null
                }
            }
        }
    }

    private fun getContactAdapter() = ContactsAdapter(
        onRemove = this::removeUser,
        onItemClickListener,
        viewModel.isRemoveState

    )

    private fun removeUser(user: User, position: Int) {
        viewModel.removeUser(user, position)
        showDeletionUndoSnackBar(user.id!!)
    }

    private fun addListeners() {
        binding.btnAdd.setOnClickListener {
            buttonClicked()
        }
    }

    private fun buttonClicked() {
        if (viewModel.isRemoveState.value == true) {
            viewModel.removeUsers()
        } else {
            AddContactDialog()
                .show(childFragmentManager, "")
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

    private fun openContactDetail(user: User) {
        findNavController().navigate(
            /*ContactsListFragmentDirections.actionContactsListFragmentToContactDetailFragment(
                user
            )*/
            ContactsListFragmentDirections.actionContactsListFragmentToContactDetailFragment(user)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val onItemClickListener = object : ContactHolder.OnItemClickListener {

        override fun onItemClick(user: User) {
            if (viewModel.isRemoveState.value == true) {
                viewModel.toggleUserRemove(user.id!!)
            } else {
                openContactDetail(user)
            }
        }

        override fun onLongItemClick(user: User): Boolean {
            viewModel.toggleUserRemove(user.id!!)
            return true
        }

    }
}