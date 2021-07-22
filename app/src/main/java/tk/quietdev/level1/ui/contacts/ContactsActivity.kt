package tk.quietdev.level1.ui.contacts


import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import tk.quietdev.level1.R
import tk.quietdev.level1.database.FakeDatabase
import tk.quietdev.level1.databinding.DialogAddContactBinding
import tk.quietdev.level1.models.User


class ContactsActivity : AppCompatActivity(), AddContactDialog.EditNameDialogListener {

    private var deletedUser = ""
    private var deletedUserPosition = 0
    private lateinit var addBackButton: Button
    private lateinit var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)
        addBackButton = findViewById(R.id.extended_fab)
        val recyclerView = findViewById<RecyclerView>(R.id.recycle_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecycleViewAdapter(FakeDatabase.userContacts, this)
        adapter = recyclerView.adapter!!
        recyclerView.addItemDecoration(DividerItemDecoration(this@ContactsActivity, LinearLayoutManager.VERTICAL))

        addListeners()

    }

    private fun addListeners() {
        addBackButton?.setOnClickListener {
            AddContactDialog()
                .show(supportFragmentManager, "MyCustomFragment")
        }
    }

    fun removeUser(position: Int) {
        deletedUser = FakeDatabase.userContacts.removeAt(position)
        deletedUserPosition = position
        adapter.notifyDataSetChanged()
        //adapter.notifyItemRemoved(position)
        Snackbar.make(
            findViewById<RecyclerView>(R.id.recycle_view),
            resources.getText(R.string.contact_removed),
            5000
        )
            .setTextColor(Color.WHITE)
            .setAction("NOO!!") {
                addUserBack(position)
            }
            .show()
    }

    private fun addUserBack(position: Int) {
        if (deletedUser.isNotEmpty()) {
            FakeDatabase.userContacts.add(position, deletedUser)
            deletedUser = ""
            adapter.notifyDataSetChanged()
        }
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

        FakeDatabase.allFakeUsers[user.email] = user
        FakeDatabase.userContacts.add(user.email)
        adapter.notifyDataSetChanged()
    }


}