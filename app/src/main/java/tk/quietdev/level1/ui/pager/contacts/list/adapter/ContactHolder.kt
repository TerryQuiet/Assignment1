package tk.quietdev.level1.ui.pager.contacts.list.adapter

import android.graphics.Color
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import tk.quietdev.level1.databinding.ListItemBinding
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.utils.ListState

class ContactHolder(
    binding: ListItemBinding?,
    onClickListener: OnItemClickListener,
    itemStateChecker: ItemStateChecker,
    holderType: HolderType
) : ContactHolderBase(binding, onClickListener, itemStateChecker, holderType) {


    private var isMultiselectState = false
    private var isSelected = false
    private var isAdded = false

    private val stateObserver = Observer<ListState> { state ->
        binding?.apply {
            when (state) {
                ListState.MULTISELECT -> {
                    cbRemove.visibility = View.VISIBLE
                    isMultiselectState = true
                }
                ListState.NORMAL -> {
                    cbRemove.visibility = View.GONE
                    isMultiselectState = false
                }
                else -> cbRemove.visibility = View.GONE
            }
        }
    }


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
                HolderType.REMOVE -> {
                    changeBackgroundColor()
                    isSelected = itemStateChecker.isItemSelected(userModel.id)
                    cbRemove.isChecked = isSelected
                    imageBtnRemove.visibility = View.VISIBLE
                    imageBtnRemove.setOnClickListener {
                        onIconClick()
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
                toggleState()
                onClickListener.onLongItemClick(currentUser)
            }
        }
    }

    private fun onItemClicked() {
        if (isMultiselectState) {
            toggleState()
        }
        onClickListener.onItemClick(currentUser)
    }

    private fun toggleState() {
        currentUser.apply {
            isSelected = !isSelected
            binding?.cbRemove?.isChecked = isSelected
        }
        changeBackgroundColor()
    }

    // TODO: 8/15/2021 get current theme colors? 
    private fun changeBackgroundColor() {
        if (isSelected) {
            binding?.layout?.setBackgroundColor(Color.GRAY)
        } else {
            binding?.layout?.setBackgroundColor(Color.TRANSPARENT)
        }
    }


    fun onIconClick() {
        onClickListener.onIconClick(currentUser, absoluteAdapterPosition)
        binding?.apply {
            if (holderType == HolderType.ADD) {
                layoutBtnAdd.visibility = View.GONE
                ivAdded.visibility = View.VISIBLE
            }
        }
    }


    fun setObserver(removeState: MutableLiveData<ListState>) {
        removeState.observeForever(stateObserver)
    }

    fun removeObserver(removeState: MutableLiveData<ListState>) {
        removeState.removeObserver(stateObserver)
    }


}