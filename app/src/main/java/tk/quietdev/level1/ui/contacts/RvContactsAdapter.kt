package tk.quietdev.level1.ui.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tk.quietdev.level1.database.FakeDatabase
import tk.quietdev.level1.databinding.ListItemBinding
import tk.quietdev.level1.utils.OnSwipeCallBack
import tk.quietdev.level1.utils.ext.loadImage

class RvContactsAdapter(
    private val inflater: LayoutInflater,
    private val onRemove: (String?) -> Unit
    ) : ListAdapter<String, RvContactsAdapter.ContactHolder>(DiffCallBack), OnSwipeCallBack.Listener  {

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val onSwipeCallBack = OnSwipeCallBack(this)
        ItemTouchHelper(onSwipeCallBack).attachToRecyclerView(recyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        return ContactHolder(ListItemBinding.inflate(inflater, parent, false), onRemove)
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onHolderSwipped(viewHolder: RecyclerView.ViewHolder) {
        (viewHolder as ContactHolder).remove()
    }

     class ContactHolder(
        private val binding: ListItemBinding,
        private val onRemove: (String?) -> Unit
    ) : RecyclerView.ViewHolder(binding.root)  {

        private var email: String? = ""

        fun bind(userID: String) {
            val user = FakeDatabase.getUserWithNoValidation(userID)

            email = user?.email
            with(user) {
                binding.tvName.text = this?.userName
                binding.tvOccupation.text = this?.occupation
                binding.ivProfilePic.loadImage(this?.picture)
                binding.imageBtnRemove.setOnClickListener {
                    remove()
                }
            }
        }
         fun remove() {
            onRemove(email)
        }
     }

    private object DiffCallBack : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }



}

