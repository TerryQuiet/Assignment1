package tk.quietdev.level1.ui.contacts


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tk.quietdev.level1.database.FakeDatabase
import tk.quietdev.level1.databinding.ListItemBinding
import tk.quietdev.level1.utils.ext.loadImage


class RecycleViewAdapter(
    private val dataset: MutableList<String>,
    private val contactsActivity: ContactsActivity
) : RecyclerView.Adapter<RecycleViewAdapter.ItemViewHolder>() {

    class ItemViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val binding = ListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val userID = FakeDatabase.userContacts[position]
        val user = FakeDatabase.getUserWithNoValidation(userID)
        with(holder) {
            with(user) {
                binding.tvName.text = this?.userName
                binding.tvOccupation.text = this?.occupation
                binding.ivProfilePic.loadImage(this?.picture)
                binding.imageBtnRemove.setOnClickListener {
                    contactsActivity.removeUser(position)
                }
            }
        }
    }

    override fun getItemCount():Int {
        return dataset.size
    } 
}