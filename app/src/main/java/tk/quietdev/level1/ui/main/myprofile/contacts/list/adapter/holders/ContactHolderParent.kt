package tk.quietdev.level1.ui.main.myprofile.contacts.list.adapter.holders

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import tk.quietdev.level1.databinding.ListItemNewBinding
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.ui.ListHolderView
import tk.quietdev.level1.utils.ext.loadImage


abstract class ContactHolderParent(
    protected val binding: ListItemNewBinding,
    protected val onClickListener: OnItemClickListener,
) : RecyclerView.ViewHolder(binding.root) {

    private var _currentUserModel: UserModel? = null
    protected val currentUser get() = _currentUserModel!!

    private val holderStateObserver = Observer<MutableMap<Int, HolderState>> { state ->
        binding.holder.buttonState = ListHolderView.ButtonState.NORMAL
        state[currentUser.id]?.let {
            binding.holder.apply {
                when (it) {
                    HolderState.PENDING -> {
                        buttonState = ListHolderView.ButtonState.SPINNER
                    }
                    HolderState.SUCCESS -> {
                        buttonState = ListHolderView.ButtonState.DONE
                    }
                    HolderState.FAIL -> {
                        //ivAdded.visibility = View.GONE
                    }
                }
            }
        }
    }

    open fun bind(userModel: UserModel) {
        binding.holder.apply {
            with(userModel) {
                _currentUserModel = this
                title.text = email
                subtitle.text = id.toString()
                picture.loadImage(pictureUri)
                setListeners()
            }
        }
    }


    protected open fun setListeners() {
        binding.root.apply {
            setOnClickListener {
                onItemClicked()
            }
            setOnLongClickListener {
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

