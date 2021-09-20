package tk.quietdev.level1.ui.main.myprofile.contacts.list.adapter.holders

import android.view.View
import tk.quietdev.level1.databinding.ListItemBinding
import tk.quietdev.level1.models.UserModel

class ContactHolderAdd(
    binding: ListItemBinding,
    onClickListener: OnItemClickListener,
) : ContactHolderBase(binding, onClickListener) {

    override fun bind(userModel: UserModel) {
        super.bind(userModel)
        binding.apply {
            layoutBtnAdd.visibility = View.VISIBLE
            ivAdded.visibility = View.GONE
            imageBtnAdd.setOnClickListener {
                onIconClick()
            }
            layoutBtnAdd.setOnClickListener {
                onIconClick()
            }
        }
    }

    override fun successState() {
        super.successState()
        binding.ivAdded.visibility = View.VISIBLE
    }

}