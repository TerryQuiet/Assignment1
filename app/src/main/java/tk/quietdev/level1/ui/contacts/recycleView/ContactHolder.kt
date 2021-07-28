package tk.quietdev.level1.ui.contacts.recycleView

import androidx.recyclerview.widget.RecyclerView
import tk.quietdev.level1.database.FakeDatabase
import tk.quietdev.level1.databinding.ListItemBinding
import tk.quietdev.level1.utils.OnItemClickListener
import tk.quietdev.level1.utils.ext.loadImage

class ContactHolder(
    private val binding: ListItemBinding,
    private val onRemove: (String?) -> Unit,
    private val functionOnClickListener: (String?) -> Unit,
    private val objectOnClickListener: OnItemClickListener
) : RecyclerView.ViewHolder(binding.root) {

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
            binding.root.setOnClickListener {
                // TODO: 7/28/2021 help me understand the difference
                functionOnClickListener(email)
                objectOnClickListener.onItemClick(email)
            }
        }
    }

    fun remove() {
        onRemove(email)
    }
}