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
import tk.quietdev.level1.ui.pager.contacts.list.adapter.RemoveContactsAdapter
import tk.quietdev.level1.ui.pager.contacts.list.adapter.HolderStateChecker

abstract class BaseListFragment : Fragment(), HolderStateChecker {

    private var _binding: FragmentContactsBinding? = null
    protected val binding get() = _binding!!
    protected val appbarSharedViewModel: AppbarSharedViewModel by activityViewModels()
    protected open val contactsAdapter: BaseContactsAdapter by lazy(mode = LazyThreadSafetyMode.NONE) { getContactAdapter() }

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

    abstract fun initObservables()

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


