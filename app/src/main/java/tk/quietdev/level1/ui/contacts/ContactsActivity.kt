package tk.quietdev.level1.ui.contacts


import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import tk.quietdev.level1.R
import tk.quietdev.level1.database.FakeDatabase
import tk.quietdev.level1.databinding.ActivityContactsBinding
import tk.quietdev.level1.databinding.DialogAddContactBinding
import tk.quietdev.level1.models.User
import tk.quietdev.level1.utils.Const
import tk.quietdev.level1.utils.OnSwipeCallBack

class ContactsActivity : AppCompatActivity() {

    private var _binding: ActivityContactsBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy { RecycleViewAdapter(viewModel) }
    lateinit var  viewModel : ContactsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ContactsViewModel::class.java)

        viewModel.apply {
            userList.observe(this@ContactsActivity, { newList ->
                adapter.update(newList)
            })
            deletedUser.observe(this@ContactsActivity, { deletedUser ->
                if (deletedUser.isNotEmpty()) {
                    showDeletionUndoSnackBar(Const.TIME_5_SEC)
                }
            })
        }

        val simpleCallback = OnSwipeCallBack(viewModel)
        binding.apply {
            recycleView.layoutManager = LinearLayoutManager(this@ContactsActivity)
            recycleView.adapter = adapter
            recycleView.addItemDecoration(DividerItemDecoration(this@ContactsActivity, LinearLayoutManager.VERTICAL))
            ItemTouchHelper(simpleCallback).attachToRecyclerView(recycleView)
        }

        adapter.update(FakeDatabase.userContacts)
        addListeners()
    }

    private fun addListeners() {
        binding.btnAdd.setOnClickListener {
            AddContactDialog()
                .show(supportFragmentManager,"")
        }
    }

    private fun showDeletionUndoSnackBar(duration: Int) {
        Snackbar.make(
            findViewById<RecyclerView>(R.id.recycle_view),
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