package tk.quietdev.level1.ui.pager.contacts.list.adapter

import android.graphics.Color
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import tk.quietdev.level1.databinding.ListItemBinding
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.utils.ListState
import tk.quietdev.level1.utils.ext.loadImage

class ContactHolder(
    private val binding: ListItemBinding,
    private val onClickListener: OnItemClickListener,
    private val itemStateChecker: ItemStateChecker,
    private val holderType: HolderType
) : RecyclerView.ViewHolder(binding.root) {

    private var _currentUserModel: UserModel? = null
    private val currentUser get() = _currentUserModel!!
    private var isMultiselectState = false
    private var isSelected = false
    private var isAdded = false

    private val stateObserver = Observer<ListState> { state ->
        when (state) {
            ListState.MULTISELECT -> {
                binding.cbRemove.visibility = View.VISIBLE
                isMultiselectState = true
            }
            ListState.NORMAL -> {
                binding.cbRemove.visibility = View.GONE
                isMultiselectState = false
            }
            else -> binding.cbRemove.visibility = View.GONE
        }
    }

    fun bind(userModel: UserModel) {
        binding.apply {
            with(userModel) {
                isSelected = itemStateChecker.isItemSelected(id)
                _currentUserModel = this
                changeBackgroundColor()
                tvName.text = email // TODO: 9/15/2021 fix
                tvOccupation.text = id.toString() // TODO: 9/15/2021 fix
                ivProfilePic.loadImage(pictureUri)
                cbRemove.isChecked = isSelected
                setListeners()
            }
            when (holderType) {
                HolderType.ADD -> {
                    isAdded = itemStateChecker.isItemAdded(userModel.id)
                    if (!isAdded) {
                        layoutBtnAdd.visibility = View.VISIBLE
                        binding.ivAdded.visibility = View.GONE
                        imageBtnAdd.setOnClickListener {
                            onIconClick()
                        }
                        layoutBtnAdd.setOnClickListener {
                            onIconClick()
                        }
                    } else {
                        layoutBtnAdd.visibility = View.GONE
                        binding.ivAdded.visibility = View.VISIBLE
                    }

                }
                HolderType.REMOVE -> {
                    imageBtnRemove.visibility = View.VISIBLE
                    imageBtnRemove.setOnClickListener {
                        onIconClick()
                    }
                }
            }
        }
    }



    private fun setListeners() {
        binding.apply {
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
            binding.cbRemove.isChecked = isSelected
        }
        changeBackgroundColor()
    }

    // TODO: 8/15/2021 get current theme colors? 
    private fun changeBackgroundColor() {
        if (isSelected) {
            binding.layout.setBackgroundColor(Color.GRAY)
        } else {
            binding.layout.setBackgroundColor(Color.TRANSPARENT)
        }
    }

    fun onIconClick() {
        onClickListener.onIconClick(currentUser, absoluteAdapterPosition)
        if (holderType == HolderType.ADD) {
            binding.layoutBtnAdd.visibility = View.GONE
            binding.ivAdded.visibility = View.VISIBLE
        }
    }

    fun setObserver(removeState: MutableLiveData<ListState>) {
        removeState.observeForever(stateObserver)
    }

    fun removeObserver(removeState: MutableLiveData<ListState>) {
        removeState.removeObserver(stateObserver)
    }

    interface OnItemClickListener {
        fun onItemClick(userModel: UserModel)
        fun onLongItemClick(userModel: UserModel): Boolean
        fun onIconClick(userModel: UserModel, position: Int)
    }

    enum class HolderType {
        REMOVE, ADD
    }

}