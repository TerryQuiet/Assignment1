package tk.quietdev.level1.ui.contacts


import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import tk.quietdev.level1.R
import tk.quietdev.level1.database.MockDatabase


class ContactsActivity : AppCompatActivity() {

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
        recyclerView.adapter = RecycleViewAdapter(MockDatabase.userContacts, this)
        adapter = recyclerView.adapter!!
        recyclerView.addItemDecoration(DividerItemDecoration(this@ContactsActivity, LinearLayoutManager.VERTICAL))

        addListeners()

    }

    private fun addListeners() {
        addBackButton?.setOnClickListener {
            addContactDialog()
        }
    }

    private fun addContactDialog() {
        val items = arrayOf("Item 1", "Item 2", "Item 3")

        MaterialAlertDialogBuilder(this)
           // .setTitle(resources.getString(R.string.title))
            .setItems(items) { dialog, which ->
                // Respond to item chosen
            }
            .show()
    }

    fun removeUser(position: Int) {
        deletedUser = MockDatabase.userContacts.removeAt(position)
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
        if (deletedUser.isNotEmpty()) { // TODO: 7/20/2021 There is a BUG with snackbar and button, so it can be pressed twice
            MockDatabase.userContacts.add(position, deletedUser)
            deletedUser = ""
            adapter.notifyDataSetChanged()
        }
    }


}