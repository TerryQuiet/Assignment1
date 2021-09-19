package tk.quietdev.level1.ui.pager.contacts.list.adapter.holders

import android.graphics.Color
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import tk.quietdev.level1.databinding.ListItemBinding
import tk.quietdev.level1.models.UserModel

class ContactHolderRemove(
    binding: ListItemBinding,
    onClickListener: OnItemClickListener,
) : ContactHolderBase(binding, onClickListener) {

    private var userModelId: Int? = null

    private val listStateObserver = Observer<List<Int>> { selectedList ->
        binding.apply {
            if (selectedList.isNotEmpty()) { // multiselectMode
                userModelId?.let {
                    if (selectedList.contains(it)) {
                        cbRemove.isChecked = true
                        layout.setBackgroundColor(Color.GRAY)
                    } else {
                        cbRemove.isChecked = false
                        layout.setBackgroundColor(Color.TRANSPARENT)
                    }
                }
                cbRemove.visibility = View.VISIBLE
            } else {
                cbRemove.visibility = View.GONE
                layout.setBackgroundColor(Color.TRANSPARENT)
            }
        }
    }

    override fun bind(userModel: UserModel) {
        super.bind(userModel)
        binding.apply {
            userModelId = userModel.id
            cbRemove.isChecked = false
            binding.layout.setBackgroundColor(Color.TRANSPARENT)
            imageBtnRemove.visibility = View.VISIBLE
            imageBtnRemove.setOnClickListener {
                onIconClick()
            }
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