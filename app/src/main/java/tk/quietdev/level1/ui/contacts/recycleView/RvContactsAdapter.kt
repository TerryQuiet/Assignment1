package tk.quietdev.level1.ui.contacts.recycleView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tk.quietdev.level1.databinding.ListItemBinding
import tk.quietdev.level1.models.User
import tk.quietdev.level1.utils.OnSwipeCallBack

class RvContactsAdapter(
    private val inflater: LayoutInflater,
    private val onRemove: (String?) -> Unit,
    private val functionOnClickListener: (String?) -> Unit,
    private val objectOnClickListener: ContactHolder.OnItemClickListener
) : ListAdapter<User, ContactHolder>(DiffCallBack), OnSwipeCallBack.Listener {

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val onSwipeCallBack = OnSwipeCallBack(this)
        ItemTouchHelper(onSwipeCallBack).attachToRecyclerView(recyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        return ContactHolder(
            ListItemBinding.inflate(
                inflater, parent, false
            ),
            onRemove,
            functionOnClickListener,
            objectOnClickListener
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

