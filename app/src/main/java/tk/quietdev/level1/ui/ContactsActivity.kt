package tk.quietdev.level1.ui

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import tk.quietdev.level1.R
import tk.quietdev.level1.RecycleViewAdapter
import tk.quietdev.level1.database.Database
import tk.quietdev.level1.models.User

private const val TAG = "ContactsActivity"

class ContactsActivity : AppCompatActivity() {

    private var deletedUser: User? = null
    private var deletedUserPosition = 0
    lateinit var addBackButton: Button
    lateinit var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
    lateinit var myDataset: MutableList<User>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)
        // Initialize data
        Log.d(TAG, "onCreate: ")
        myDataset = Database.getUserList().toMutableList()
        addBackButton = findViewById(R.id.extended_fab)
        val recyclerView = findViewById<RecyclerView>(R.id.recycle_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecycleViewAdapter(myDataset, this)
        adapter = recyclerView.adapter!!
        recyclerView.addItemDecoration(DividerItemDecoration(this@ContactsActivity, LinearLayoutManager.VERTICAL))

        addBackButton.setOnClickListener {
           /* addUserBack(deletedUserPosition)
            addBackButton.visibility = View.GONE*/
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }
    }

    fun removeUser(position: Int) {
        Log.d(TAG, "removeUser: $position")
        deletedUser = myDataset.removeAt(position)
        deletedUserPosition = position
        adapter.notifyDataSetChanged()
        //adapter.notifyItemRemoved(position)
        Snackbar.make(
            findViewById<RecyclerView>(R.id.recycle_view),
            resources.getText(R.string.contact_removed),
            5000
        )
            .setTextColor(Color.WHITE) // I tried to change it in theme.xml, but it doesn't work... help required
            .setAction("NOO!!") {
                addUserBack(position)
            }
            .show()
        addBackButton.visibility = View.VISIBLE
    }

    private fun addUserBack(position: Int) {
        if (deletedUser != null) { // TODO: 7/20/2021 There is a BUG with snackbar and button, so it can be pressed twice
            myDataset.add(position, deletedUser!!)
            deletedUser = null
            addBackButton.visibility = View.GONE
            adapter.notifyDataSetChanged()
        }
    }


}