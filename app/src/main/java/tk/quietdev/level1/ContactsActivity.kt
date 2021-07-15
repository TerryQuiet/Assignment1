package tk.quietdev.level1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tk.quietdev.level1.data.DB

private const val TAG = "ContactsActivity"
class ContactsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)

        // Initialize data.
        val myDataset = DB().getUserList()
        Log.d(TAG, "onCreate: ")
        val recyclerView = findViewById<RecyclerView>(R.id.recycle_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecycleViewAdapter(myDataset)
        recyclerView.setHasFixedSize(true)
    }
}