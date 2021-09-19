package tk.quietdev.level1.ui.pager.contacts.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import tk.quietdev.level1.databinding.FragmentContactsBinding
import tk.quietdev.level1.ui.pager.AppbarSharedViewModel
import tk.quietdev.level1.ui.pager.contacts.list.adapter.BaseContactsAdapter

abstract class BaseListFragment : Fragment() {

    private var _binding: FragmentContactsBinding? = null
    protected val binding get() = _binding!!
    private val appbarSharedViewModel: AppbarSharedViewModel by activityViewModels()
    protected open val contactsAdapter: BaseContactsAdapter by lazy(mode = LazyThreadSafetyMode.NONE) { getContactAdapter() }

    protected abstract val viewModel: BaseListViewModel

    abstract fun getContactAdapter(): BaseContactsAdapter

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
    }

    open fun initObservables() {
        viewModel.apply {
            userList.observe(viewLifecycleOwner) {
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
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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


