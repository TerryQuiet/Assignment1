package tk.quietdev.level1.ui.contacts


import android.graphics.Color
import android.media.MediaRouter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
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

private const val TAG = "ContactsActivity"
class ContactsActivity : AppCompatActivity(), AddContactDialog.EditNameDialogListener {

    private var deletedUser = ""
    private var deletedUserPosition = 0
    private var _binding : ActivityContactsBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy { RecycleViewAdapter(this) }

    private var simpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val email = (viewHolder as RecycleViewAdapter.ItemViewHolder).email
            removeUser(email)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                .show(supportFragmentManager, "MyCustomFragment")
        }
    }



    private fun showDeletionUndoSnackBar(duration : Int) {
        Snackbar.make(
            findViewById<RecyclerView>(R.id.recycle_view),
            resources.getText(R.string.contact_removed),
            duration
        )
            .setTextColor(Color.WHITE)
            .setAction(getString(R.string.add_back)) {
                addUserBack()
            }
            .show()
    }



    private fun addUserBack() {
        if (deletedUser.isNotEmpty()) {
            FakeDatabase.userContacts.add(deletedUserPosition, deletedUser)
            adapter.update(FakeDatabase.userContacts)
            deletedUser = ""
        }
    }

    private fun addNewUserToDatabase(user: User) {
        FakeDatabase.allFakeUsers[user.email] = user
        FakeDatabase.userContacts.add(user.email)
        adapter.update(FakeDatabase.userContacts)
    }

    fun removeUser(email: String?) {
        val position = FakeDatabase.userContacts.indexOf(email)
        deletedUser = FakeDatabase.userContacts.removeAt(position)
        deletedUserPosition = position
        adapter.update(FakeDatabase.userContacts)
        showDeletionUndoSnackBar(Const.TIME_5_SEC)
    }

    override fun onDialogAddClicked(dialogBinding: DialogAddContactBinding) {
        val name = dialogBinding.etName.text.toString()
        val surname = dialogBinding.etSurname.text.toString()
        val occupation = dialogBinding.etOccupation.text.toString()

        val user = User(
            userName = "$name $surname",
            email = "$name.$surname@mail.fake",
            occupation = occupation
        )
        addNewUserToDatabase(user)

    }






}