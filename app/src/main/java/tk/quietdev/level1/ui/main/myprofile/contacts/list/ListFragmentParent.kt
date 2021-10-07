package tk.quietdev.level1.ui.main.myprofile.contacts.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import tk.quietdev.level1.BaseFragment
import tk.quietdev.level1.databinding.FragmentContactsBinding
import tk.quietdev.level1.ui.main.AppbarSharedViewModel
import tk.quietdev.level1.ui.main.myprofile.contacts.list.adapter.BaseContactsAdapter

abstract class ListFragmentParent :
    BaseFragment<FragmentContactsBinding>(FragmentContactsBinding::inflate) {

    private val appbarSharedViewModel: AppbarSharedViewModel by activityViewModels()
    protected open val contactsAdapter: BaseContactsAdapter by lazy(mode = LazyThreadSafetyMode.NONE) { getContactAdapter() }
    protected abstract val viewModel: ParentListViewModel

    abstract fun getContactAdapter(): BaseContactsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycleView()
    }

    override fun setObservers() {
        viewModel.apply {
            userListToShow.observe(viewLifecycleOwner) {
                val list = it.data
                list?.let { userList ->
                    contactsAdapter.submitList(userList)
                }
            }
            appbarSharedViewModel.searchText.observe(viewLifecycleOwner) {
                viewModel.changeSearchQuery(it)
            }
        }
    }

    private fun initRecycleView() {
        binding.recycleView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = contactsAdapter
        }
    }

    override fun onPause() {
        super.onPause()
        appbarSharedViewModel.searchIconVisibility.value = View.GONE
        appbarSharedViewModel.showSearchLayout(false)
    }

    override fun onResume() {
        super.onResume()
        appbarSharedViewModel.searchIconVisibility.value = View.VISIBLE
    }
}


