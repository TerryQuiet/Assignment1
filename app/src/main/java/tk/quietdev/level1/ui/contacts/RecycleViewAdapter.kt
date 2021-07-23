package tk.quietdev.level1.ui.contacts


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import tk.quietdev.level1.database.FakeDatabase
import tk.quietdev.level1.databinding.ListItemBinding
import tk.quietdev.level1.utils.ext.loadImage


class RecycleViewAdapter(
    private val contactsActivity: ContactsActivity
) : RecyclerView.Adapter<RecycleViewAdapter.ItemViewHolder>() {

    private var userList = emptyList<String>()

    fun update(newList: List<String>) {
        val callback = DiffUtilCallback(userList, newList)
        val result = DiffUtil.calculateDiff(callback)
        userList = newList.toList()
        result.dispatchUpdatesTo(this)
    }

    class ItemViewHolder(
        val binding: ListItemBinding,
        var email: String? = ""
    ) : RecyclerView.ViewHolder(binding.root)

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
                    contactsActivity.removeUser(this?.email)
                }
                email = this?.email
            }
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class DiffUtilCallback(private val oldList: List<String>, private val newList: List<String>) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] === newList[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}