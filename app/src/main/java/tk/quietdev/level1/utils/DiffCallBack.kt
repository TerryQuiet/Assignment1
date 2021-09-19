package tk.quietdev.level1.utils

import androidx.recyclerview.widget.DiffUtil
import tk.quietdev.level1.models.UserModel

object DiffCallBack : DiffUtil.ItemCallback<UserModel>() {
    override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        return oldItem == newItem
    }
}