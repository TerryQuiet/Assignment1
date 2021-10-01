package tk.quietdev.level1.ui.main.myprofile.contacts.list.adapter.holders

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import tk.quietdev.level1.databinding.ListItemNewBinding
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.ui.ListHolderView

class ContactHolderRemove(
    binding: ListItemNewBinding,
    onClickListener: OnItemClickListener,
) : ContactHolderParent(binding, onClickListener) {

    private var userModelId: Int? = null

    private val listStateObserver = Observer<List<Int>> { selectedList ->
        binding.apply {
            if (selectedList.isNotEmpty()) { // multiselectMode
                binding.holder.multiselectState = true
                userModelId?.let {
                    binding.holder.isHolderChecked = selectedList.contains(it)
                }
            } else {
                binding.holder.multiselectState = false
            }
        }
    }

    override fun bind(userModel: UserModel) {
        super.bind(userModel)
        binding.apply {
            binding.holder.holderType = ListHolderView.HolderType.REMOVE
            userModelId = userModel.id
            binding.holder.isHolderChecked = false
            binding.holder.setOnButtonClickListener { onIconClick() }
        }
    }

    override fun setListeners() {
        super.setListeners()
        binding.apply {
            root.setOnLongClickListener {
                onClickListener.onLongItemClick(currentUser)
            }
        }
    }

    fun setListObserver(removeState: MutableLiveData<List<Int>>) {
        removeState.observeForever(listStateObserver)
    }

    fun removeListObserver(removeState: MutableLiveData<List<Int>>) {
        removeState.removeObserver(listStateObserver)
    }


}