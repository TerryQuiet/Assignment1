package tk.quietdev.level1.ui.pager.contacts.list.adapter

import android.view.View
import tk.quietdev.level1.databinding.ListItemBinding
import tk.quietdev.level1.models.UserModel

class ContactHolderAdd(
    binding: ListItemBinding,
    onClickListener: OnItemClickListener,
    itemStateChecker: ItemStateChecker,
    holderType: HolderType
) : ContactHolderBase(binding, onClickListener, itemStateChecker, holderType) {

    private var isAdded = false

    override fun bindSpecial(userModel: UserModel) {
        binding?.apply {
            when (holderType) {
                HolderType.ADD -> {
                    isAdded = itemStateChecker.isItemAdded(userModel.id)
                    if (!isAdded) {
                        layoutBtnAdd.visibility = View.VISIBLE
                        ivAdded.visibility = View.GONE
                        imageBtnAdd.setOnClickListener {
                            onIconClick()
                        }
                        layoutBtnAdd.setOnClickListener {
                            onIconClick()
                        }
                    } else {
                        layoutBtnAdd.visibility = View.GONE
                        ivAdded.visibility = View.VISIBLE
                    }

                }
            }
        }
    }

    override fun setListeners() {
        binding?.apply {
            cbRemove.setOnClickListener {
                onItemClicked()
            }
            root.setOnClickListener {
                onItemClicked()
            }
            root.setOnLongClickListener {
                onClickListener.onLongItemClick(currentUser)
            }
        }
    }

    private fun onItemClicked() {
        onClickListener.onItemClick(currentUser)
    }

    private fun onIconClick() {
        onClickListener.onIconClick(currentUser, absoluteAdapterPosition)
        binding?.apply {
            if (holderType == HolderType.ADD) {
                layoutBtnAdd.visibility = View.GONE
                ivAdded.visibility = View.VISIBLE
            }
        }
    }

}