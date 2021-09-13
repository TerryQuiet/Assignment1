package tk.quietdev.level1.ui.pager.contacts.adapter

import android.graphics.Color
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import tk.quietdev.level1.R
import tk.quietdev.level1.databinding.ListItemBinding
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.ui.pager.contacts.list.ContactListViewModel
import tk.quietdev.level1.ui.pager.contacts.list.ListState
import tk.quietdev.level1.utils.ext.loadImage

class ContactHolder(
    private val binding: ListItemBinding,
    private val onRemove: (UserModel, Int) -> Unit,
    private val onClickListener: OnItemClickListener,
    private val itemStateChecker: ItemStateChecker
) : RecyclerView.ViewHolder(binding.root) {

    private var _currentUserModel: UserModel? = null
    private val currentUser get() = _currentUserModel!!
    private var isRemoveState = false
    private var isSelected = false


    private val stateObserver = Observer<ListState> { removeState ->
        when (removeState) {
            ListState.DELETION ->  {
                bindDeletion()
                isRemoveState = true
            }
            ListState.NORMAL -> {
                bindNormal()
                isRemoveState = false
            }
            else -> bindAddition()
        }
    }

    private fun bindAddition() {
        binding.cbRemove.visibility = View.GONE
        val icon = AppCompatResources.getDrawable(binding.root.context, R.drawable.ic_add)
        binding.imageBtnRemove.setImageDrawable(icon)
    }

    private fun bindNormal() {
        binding.cbRemove.visibility = View.GONE
        val icon = AppCompatResources.getDrawable(binding.root.context, R.drawable.ic_trashcan)
        binding.imageBtnRemove.setImageDrawable(icon)
    }

    private fun bindDeletion() {
        binding.cbRemove.visibility = View.VISIBLE
    }

    fun bind(userModel: UserModel) {
        with(userModel) {
            isSelected = itemStateChecker.isItemSelected(id!!)
            _currentUserModel = this
            changeBackgroundColor()
            binding.tvName.text = userName
            binding.tvOccupation.text = occupation
            binding.ivProfilePic.loadImage(pictureUri)
            binding.cbRemove.isChecked = isSelected
            setListeners()
        }
    }

    private fun setListeners() {
        binding.apply {
            imageBtnRemove.setOnClickListener {
                remove()
            }
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
        if (isRemoveState) {
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

    fun remove() {
        onRemove(currentUser, absoluteAdapterPosition)
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
    }

    interface ItemStateChecker {
        fun isItemSelected(id: Int) : Boolean
    }



}