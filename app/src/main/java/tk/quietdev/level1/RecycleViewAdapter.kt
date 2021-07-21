package tk.quietdev.level1


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tk.quietdev.level1.models.User
import tk.quietdev.level1.databinding.ListItemBinding
import tk.quietdev.level1.ui.ContactsActivity
import tk.quietdev.level1.utils.ext.loadImage

private const val TAG = "RecycleViewAdapter"

class RecycleViewAdapter(
    private val dataset: MutableList<User>,
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
        with(holder) {
            with(dataset[position]) {
                binding.tvName.text = this.userName
                binding.tvOccupation.text = this.occupation
                binding.ivProfilePic.loadImage(this.picture)
                binding.imageBtnRemove.setOnClickListener {
                    contactsActivity.removeUser(position)
                }
            }
        }
    }

    override fun getItemCount():Int {
        Log.d(TAG, "getItemCount: ${dataset.size}")
        return dataset.size
    } 
}