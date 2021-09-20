package tk.quietdev.level1.ui.main.myprofile.contacts.list.adapter.holders


import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import tk.quietdev.level1.databinding.ListItemBinding
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.ui.main.myprofile.contacts.list.adapter.HolderState
import tk.quietdev.level1.utils.ext.loadImage


abstract class ContactHolderBase(
    protected val binding: ListItemBinding,
    protected val onClickListener: OnItemClickListener,
) : RecyclerView.ViewHolder(binding.root) {

    private var _currentUserModel: UserModel? = null
    protected val currentUser get() = _currentUserModel!!

    private val holderStateObserver = Observer<MutableMap<Int, HolderState>> { state ->
        binding.spinner.visibility = View.GONE
        state[currentUser.id]?.let {
            binding.apply {
                when (it) {
                    HolderState.PENDING -> {
                        spinner.visibility = View.VISIBLE
                        layoutBtnAdd.visibility = View.GONE
                        imageBtnRemove.visibility = View.GONE
                    }
                    HolderState.SUCCESS -> {
                        addedState()
                    }
                    HolderState.FAIL -> {
                        spinner.visibility = View.GONE
                        ivAdded.visibility = View.GONE
                    }
                }
            }
        }
    }

    protected open fun addedState() {
        binding.apply {
            spinner.visibility = View.GONE
            layoutBtnAdd.visibility = View.GONE
            ivAdded.visibility = View.GONE
        }
    }


    open fun bind(userModel: UserModel) {
        binding.apply {

            with(userModel) {
                _currentUserModel = this
                tvName.text = email // TODO: 9/15/2021 fix
                tvOccupation.text = id.toString() // TODO: 9/15/2021 fix
                ivProfilePic.loadImage(pictureUri)
                setListeners()
            }
        }
    }


    protected open fun setListeners() {
        binding.apply {
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

    open fun onItemClicked() {
        onClickListener.onItemClick(currentUser)
    }

    fun removeHolderStateObserver(holderState: MutableLiveData<MutableMap<Int, HolderState>>) {
        holderState.removeObserver(holderStateObserver)
    }

    fun setHolderStateObserver(holderState: MutableLiveData<MutableMap<Int, HolderState>>) {
        holderState.observeForever(holderStateObserver)
    }

    fun onIconClick() {
        onClickListener.onIconClick(currentUser, absoluteAdapterPosition)
    }

    interface OnItemClickListener {
        fun onItemClick(userModel: UserModel)
        fun onLongItemClick(userModel: UserModel): Boolean
        fun onIconClick(userModel: UserModel, position: Int)
    }

}

