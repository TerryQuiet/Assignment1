package tk.quietdev.level1.ui.pager.contacts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tk.quietdev.level1.databinding.ListItemBinding
import tk.quietdev.level1.models.User
import tk.quietdev.level1.utils.OnSwipeCallBack

class ContactsAdapter(
    private val onRemove: (User, Int) -> Unit,
    private val onItemClickListener: (User) -> Unit
) : ListAdapter<User, ContactHolder>(DiffCallBack), OnSwipeCallBack.Listener {

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {

        super.onAttachedToRecyclerView(recyclerView)
        val onSwipeCallBack = OnSwipeCallBack(this)
        ItemTouchHelper(onSwipeCallBack).attachToRecyclerView(recyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        return ContactHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            onRemove,
            onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onHolderSwiped(viewHolder: RecyclerView.ViewHolder) {
        (viewHolder as ContactHolder).remove()
    }

    private object DiffCallBack : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

    }

}

