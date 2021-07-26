package tk.quietdev.level1.ui.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tk.quietdev.level1.database.FakeDatabase
import tk.quietdev.level1.databinding.ListItemBinding
import tk.quietdev.level1.utils.ext.loadImage

class RecycleViewAdapter2(
    private val inflater: LayoutInflater,
    private val viewModel: ContactsViewModel
) : ListAdapter<String, RecycleViewAdapter2.RowHolder>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        return RowHolder(ListItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RowHolder(
        val binding: ListItemBinding,
        var email: String? =""
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(userID: String) {
            val user = FakeDatabase.getUserWithNoValidation(userID)
            with(user) {
                binding.tvName.text = this?.userName
                binding.tvOccupation.text = this?.occupation
                binding.ivProfilePic.loadImage(this?.picture)
                binding.imageBtnRemove.setOnClickListener {
                    viewModel.removeUser(this?.email)

                }
                email = this?.email
            }

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

