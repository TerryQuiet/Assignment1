package tk.quietdev.level1.ui.pager.contacts.list.adapter


import androidx.recyclerview.widget.RecyclerView
import tk.quietdev.level1.databinding.ListItemBinding
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.utils.ext.loadImage


abstract class ContactHolderBase(
    var binding: ListItemBinding?,
    protected val onClickListener: OnItemClickListener,
    protected val itemStateChecker: ItemStateChecker,
    protected val holderType: HolderType
) : RecyclerView.ViewHolder(binding!!.root) {

    private var _currentUserModel: UserModel? = null
    protected val currentUser get() = _currentUserModel!!

    interface OnItemClickListener {
        fun onItemClick(userModel: UserModel)
        fun onLongItemClick(userModel: UserModel): Boolean
        fun onIconClick(userModel: UserModel, position: Int)
    }

    fun bind(userModel: UserModel) {
        binding?.apply {
            with(userModel) {
                _currentUserModel = this
                tvName.text = email // TODO: 9/15/2021 fix
                tvOccupation.text = id.toString() // TODO: 9/15/2021 fix
                ivProfilePic.loadImage(pictureUri)
                setListeners()
            }
            bindSpecial(userModel)
        }
    }

    abstract fun bindSpecial(userModel: UserModel)

    abstract fun setListeners()

    enum class HolderType {
        REMOVE, ADD
    }

}

