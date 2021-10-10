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
) : ParentContactHolder(binding, onClickListener) {

    private val listStateObserver = Observer<List<Int>> { selectedList ->
        binding.apply {
            val isMultiselect = selectedList.isNotEmpty()
            showMultiSelect(isMultiselect)
            val isSelected = if (isMultiselect) selectedList.contains(currentUser.id) else false
            showHolderSelected(isSelected)
        }
    }

    private fun showHolderSelected(isSelected: Boolean) {
        binding.cbRemove.isChecked = isSelected
        binding.background.setBackgroundColor(if (isSelected) Color.GRAY else Color.TRANSPARENT)
    }

    private fun showMultiSelect(isMultiSelect: Boolean) {
        binding.cbRemove.visibility = if (isMultiSelect) View.VISIBLE else View.GONE
    }

    override fun bind(userModel: UserModel) {
        super.bind(userModel)
        showHolderSelected(false)
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