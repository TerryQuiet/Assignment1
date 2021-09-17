package tk.quietdev.level1.ui.pager.contacts.list.addcontacts

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
import androidx.lifecycle.lifecycleScope
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
import tk.quietdev.level1.ui.pager.AppbarSharedViewModel
import tk.quietdev.level1.ui.pager.contacts.list.adapter.ItemStateChecker
import tk.quietdev.level1.ui.pager.contacts.list.adapter.ContactHolder
import tk.quietdev.level1.ui.pager.contacts.list.adapter.ContactsAdapter
import tk.quietdev.level1.utils.Const
import tk.quietdev.level1.utils.ListState

@AndroidEntryPoint
class AddContactsListFragment : Fragment(), ItemStateChecker {

    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddContactListViewModel by viewModels()
    private val contactsAdapter: ContactsAdapter by lazy(mode = LazyThreadSafetyMode.NONE) { getContactAdapter() }
    private val appbarSharedViewModel: AppbarSharedViewModel by activityViewModels()

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
            listState.observe(viewLifecycleOwner) { state ->
                when (state) {
                    ListState.MULTISELECT -> {
                        binding.btnAdd.text = getString(R.string.add_contact)
                        binding.btnAdd.visibility = View.VISIBLE
                    }
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

    }

    // works

    private fun getContactAdapter() = ContactsAdapter(
        onItemClickListener,
        viewModel.listState,
        this,
        ContactHolder.HolderType.ADD

    )

    private fun addUser(userModel: UserModel) {
        viewModel.addUserContact(userModel)
        //showDeletionUndoSnackBar(userModel.id)
    }

    private fun addListeners() {
        binding.btnAdd.setOnClickListener {
            fabClicked()
        }
        appbarSharedViewModel.isSearchIconClicked.observe(viewLifecycleOwner) {
            if (it) {

            }
        }
    }

    private fun fabClicked() {
        if (viewModel.listState.value == ListState.MULTISELECT) {
        //    viewModel.removeUsers() // todo add users
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
            AddContactsListFragmentDirections.actionAddContactsListFragmentToContactDetailFragment(
                userModel
            )
        )
    }

    override fun onPause() {
        super.onPause()
        appbarSharedViewModel.searchIconVisibility.value = View.GONE
    }

    override fun onResume() {
        super.onResume()
        appbarSharedViewModel.searchIconVisibility.value = View.VISIBLE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val onItemClickListener = object : ContactHolder.OnItemClickListener {

        override fun onItemClick(userModel: UserModel) {
            if (viewModel.listState.value == ListState.MULTISELECT) {
            } else {
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