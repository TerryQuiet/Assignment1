package tk.quietdev.level1.ui.contacts


import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import tk.quietdev.level1.R
import tk.quietdev.level1.database.FakeDatabase
import tk.quietdev.level1.databinding.DialogAddContactBinding
import tk.quietdev.level1.models.User
import tk.quietdev.level1.utils.Const


class ContactsActivity : AppCompatActivity(), AddContactDialog.EditNameDialogListener {

    private var deletedUser = ""
    private var deletedUserPosition = 0
    private lateinit var addBackButton: Button
    private lateinit var adapter: RecycleViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)
        addBackButton = findViewById(R.id.extended_fab)
        val recyclerView = findViewById<RecyclerView>(R.id.recycle_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecycleViewAdapter( this)
        adapter = (recyclerView.adapter as RecycleViewAdapter)
        adapter.update(FakeDatabase.userContacts)
        recyclerView.addItemDecoration(DividerItemDecoration(this@ContactsActivity, LinearLayoutManager.VERTICAL))
        addListeners()

    }

    private fun addListeners() {
        addBackButton?.setOnClickListener {
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

    fun removeUser(position: String?) {
        removeUser(FakeDatabase.userContacts.indexOf(position))
    }

    private fun addUserBack() {
        if (deletedUser.isNotEmpty()) {
            FakeDatabase.userContacts.add(deletedUserPosition, deletedUser)
            adapter.update(FakeDatabase.userContacts)
            deletedUser = ""
            //adapter.notifyItemInserted(deletedUserPosition)
        }
    }

    private fun addNewUserToDatabase(user: User) {
        FakeDatabase.allFakeUsers[user.email] = user
        FakeDatabase.userContacts.add(user.email)
        // adapter.notifyItemInserted(FakeDatabase.userContacts.size)
        adapter.update(FakeDatabase.userContacts)
    }

    fun removeUser(position: Int) {
        deletedUser = FakeDatabase.userContacts.removeAt(position)
        deletedUserPosition = position
        //adapter.notifyItemRemoved(position)
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