package tk.quietdev.level1.ui.contacts

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import tk.quietdev.level1.R
import tk.quietdev.level1.database.FakeDatabase
import tk.quietdev.level1.databinding.FragmentContactsBinding
import tk.quietdev.level1.utils.Const
import tk.quietdev.level1.utils.OnSwipeCallBack

private const val TAG = "ContactsFragment"
class ContactsFragment : Fragment() {
    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy { RecycleViewAdapter(viewModel) }
    val viewModel: ContactsViewModel by viewModels()

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentContactsBinding.inflate(inflater, container, false).apply { _binding = this }.root



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.apply {
            userList.observe(viewLifecycleOwner, { newList ->
                adapter.update(newList)
            })
            deletedUser.observe(viewLifecycleOwner, { deletedUser ->
                if (deletedUser.isNotEmpty()) {
                    showDeletionUndoSnackBar(Const.TIME_5_SEC)
                }
            })
        }

        val simpleCallback = OnSwipeCallBack(viewModel)
        binding.apply {
            recycleView.layoutManager = LinearLayoutManager(context)
            recycleView.adapter = adapter
            recycleView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            ItemTouchHelper(simpleCallback).attachToRecyclerView(recycleView)
        }
        Log.d(TAG, "onViewCreated: ${FakeDatabase.userContacts.size}" )
        adapter.update(FakeDatabase.userContacts)
        addListeners()
    }

    private fun addListeners() {
        binding.btnAdd.setOnClickListener {
            AddContactDialog()
                .show(childFragmentManager, "")
        }
    }

    private fun showDeletionUndoSnackBar(duration: Int) {
        Snackbar.make(
            binding.btnAdd,
            getString(R.string.contact_removed),
            duration
        )
            .setTextColor(Color.WHITE)
            .setAction(getString(R.string.add_back)) {
                viewModel.addUserBack()
            }
            .show()
    }


}