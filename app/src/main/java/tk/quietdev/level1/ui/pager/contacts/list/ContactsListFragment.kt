package tk.quietdev.level1.ui.pager.contacts.list

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import tk.quietdev.level1.R
import tk.quietdev.level1.databinding.FragmentContactsBinding
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.ui.pager.contacts.ContactsSharedViewModel
import tk.quietdev.level1.ui.pager.contacts.adapter.ContactHolder
import tk.quietdev.level1.ui.pager.contacts.adapter.ContactsAdapter
import tk.quietdev.level1.ui.pager.contacts.dialog.AddContactDialog
import tk.quietdev.level1.utils.Const

@AndroidEntryPoint
class ContactsListFragment : Fragment(), ContactHolder.ItemStateChecker {

    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ContactListViewModel by viewModels()
    private val contactsSharedViewModel: ContactsSharedViewModel by activityViewModels()
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
        }
    }









    // works

    private fun getContactAdapter() = ContactsAdapter(
        onRemove = this::removeUser,
        onItemClickListener,
        viewModel.isRemoveState,
        this
    )

    private fun removeUser(userModel: UserModel, position: Int) {
        viewModel.removeUser(userModel, position)
        showDeletionUndoSnackBar(userModel.id!!)
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

    private fun openContactDetail(userModel: UserModel) {
        findNavController().navigate(
            ContactsListFragmentDirections.actionContactsListFragmentToContactDetailFragment(userModel)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val onItemClickListener = object : ContactHolder.OnItemClickListener {

        override fun onItemClick(userModel: UserModel) {
            if (viewModel.isRemoveState.value == true) {
                viewModel.toggleUserSelected(userModel.id!!)
            } else {
                openContactDetail(userModel)
            }
        }

        override fun onLongItemClick(userModel: UserModel): Boolean {
            viewModel.toggleUserSelected(userModel.id!!)
            return true
        }

    }

    override fun isItemSelected(id:Int): Boolean {
       return viewModel.isItemSelected(id)
    }
}