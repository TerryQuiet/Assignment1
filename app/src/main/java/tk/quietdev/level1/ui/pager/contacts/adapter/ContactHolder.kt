package tk.quietdev.level1.ui.pager.contacts.adapter

import androidx.recyclerview.widget.RecyclerView
import tk.quietdev.level1.databinding.ListItemBinding
import tk.quietdev.level1.models.User

class ContactHolder(
    private val binding: ListItemBinding,
    private val onRemove: (User, Int) -> Unit,
    private val onClickListener: (User) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var currentUserId: User? = null
    
    fun bind(user: User) {
        with(user) {
            currentUserId = this
            binding.tvName.text = this.userName
            binding.tvOccupation.text = this.occupation
            binding.ivProfilePic.loadImage(this.pictureUri)
            binding.imageBtnRemove.setOnClickListener {
                remove()
            }
            binding.root.setOnClickListener {
                 onClickListener(this)
            }
        }
    }

    fun remove() {
        currentUserId?.let {
            onRemove(it, absoluteAdapterPosition)
        }

    }
}