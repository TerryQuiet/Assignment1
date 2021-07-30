package tk.quietdev.level1.ui.contacts

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import tk.quietdev.level1.R
import tk.quietdev.level1.databinding.DialogAddContactBinding
import tk.quietdev.level1.databinding.FragmentContactsBinding
import tk.quietdev.level1.models.User
import tk.quietdev.level1.ui.contacts.recycleView.RvContactsAdapter
import tk.quietdev.level1.utils.Const
import tk.quietdev.level1.utils.OnItemClickListener


class ContactsFragment : Fragment(), AddContactDialog.Listener {

    private lateinit var binding: FragmentContactsBinding
    private lateinit var viewModel: ContactsViewModel
    private val adapter by lazy {
        RvContactsAdapter(
            layoutInflater,
            this::removeUser,
            // TODO: 7/28/2021 help me understand the difference
            functionOnClickListener =  this::openContactDetail,
            objectOnClickListener =  object : OnItemClickListener {
                override fun onItemClick(id: String?) {
                    openContactDetail(id)
                }
            }
        )
    }

    private fun removeUser(email: String?) {
        email?.let {
            viewModel.removeUser(email)
            showDeletionUndoSnackBar(email ,Const.TIME_5_SEC)
        }
    }

    private fun addListeners() {
        binding.btnAdd.setOnClickListener {
            AddContactDialog(this)
                .show(childFragmentManager, "")
        }
    }


    private fun showDeletionUndoSnackBar(email :String , duration: Int) {
        Snackbar.make(
            binding.btnAdd,
            getString(R.string.contact_removed),
            duration
        )
            .setTextColor(Color.WHITE)
            .setAction(getString(R.string.add_back)) {
                viewModel.addUserBack(email)
            }
            .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                       viewModel.setUserRecoverable(email, false)
                }
            })
            .show()
    }

    private fun openContactDetail(email: String?) {
        findNavController().navigate(ContactsFragmentDirections.actionContactsFragmentToContactDetailFragment(email))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentContactsBinding.inflate(inflater, container, false).apply { binding = this }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ContactsViewModel::class.java)

        viewModel.apply {
            userList.observe(viewLifecycleOwner) { newList ->
                adapter.submitList(getUsersByEmail(newList))
            }
        }

        binding.apply {
            recycleView.layoutManager = LinearLayoutManager(context)
            recycleView.adapter = adapter
            recycleView.addItemDecoration(
                DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            )
        }
        adapter.submitList(getUsersByEmail(viewModel.userList.value!!))
        addListeners()
    }

    private fun getUsersByEmail(value: MutableList<String>): List<User> {
      return value.mapNotNull { viewModel.getUser(it) }.toList()
    }

    override fun onDialogAddClicked(binding: DialogAddContactBinding) {
        viewModel.onDialogAddClicked(binding)
    }

}