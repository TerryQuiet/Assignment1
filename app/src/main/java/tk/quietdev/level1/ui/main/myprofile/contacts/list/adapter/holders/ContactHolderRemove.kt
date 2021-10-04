package tk.quietdev.level1.ui.main.myprofile.contacts.list.adapter.holders

import android.graphics.Color
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import tk.quietdev.level1.databinding.ListItemBinding
import tk.quietdev.level1.models.UserModel

class ContactHolderRemove(
    binding: ListItemBinding,
    onClickListener: OnItemClickListener,
) : ContactHolderParent(binding, onClickListener) {

    private var userModelId: Int? = null

    private val listStateObserver = Observer<List<Int>> { selectedList ->
        binding.apply {
            val isMultiselect = selectedList.isNotEmpty()
            showMultiSelect(isMultiselect)
            if (isMultiselect) { // multiselectMode
                userModelId?.let {
                    showHolderSelected(selectedList.contains(it))

                }
            }
        }
    }

    private fun showHolderSelected(contains: Boolean) {
        binding.cbRemove.isChecked = contains
        if (contains) {
            binding.background.setBackgroundColor(Color.GRAY)
        } else {
            binding.background.setBackgroundColor(Color.TRANSPARENT)
        }
    }

    private fun showMultiSelect(b: Boolean) {
        binding.cbRemove.visibility = if (b) View.VISIBLE else View.GONE
    }

    override fun bind(userModel: UserModel) {
        super.bind(userModel)
        binding.apply {
            userModelId = userModel.id
            //isHolderChecked = false
        }
    }

    override fun setListeners() {
        super.setListeners()
        binding.root.setOnLongClickListener {
            onClickListener.onLongItemClick(currentUser)
        }
    }

    fun setListObserver(removeState: MutableLiveData<List<Int>>) {
        removeState.observeForever(listStateObserver)
    }

    fun removeListObserver(removeState: MutableLiveData<List<Int>>) {
        removeState.removeObserver(listStateObserver)
    }


}