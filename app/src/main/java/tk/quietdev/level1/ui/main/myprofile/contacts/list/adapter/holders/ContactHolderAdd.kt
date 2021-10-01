package tk.quietdev.level1.ui.main.myprofile.contacts.list.adapter.holders

import tk.quietdev.level1.databinding.ListItemNewBinding
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.ui.ListHolderView

class ContactHolderAdd(
    binding: ListItemNewBinding,
    onClickListener: OnItemClickListener,
) : ContactHolderParent(binding, onClickListener) {

    override fun bind(userModel: UserModel) {
        super.bind(userModel)
        binding.holder.apply {
            holderType = ListHolderView.HolderType.ADD
            setOnButtonClickListener {
                onIconClick()
            }
        }
    }

}