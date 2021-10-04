package tk.quietdev.level1.ui.main.myprofile.contacts.list.adapter.holders

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import tk.quietdev.level1.databinding.ListItemBinding
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.utils.ext.loadImage


abstract class ContactHolderParent(
    protected val binding: ListItemBinding,
    protected val onClickListener: OnItemClickListener,
) : RecyclerView.ViewHolder(binding.root) {

    private var _currentUserModel: UserModel? = null
    protected val currentUser get() = _currentUserModel!!

    private val holderStateObserver = Observer<MutableMap<Int, HolderState>> { state ->
        showNormalState()
        state[currentUser.id]?.let {
            binding.apply {
                when (it) {
                    HolderState.PENDING -> {
                        spinner.visibility = View.VISIBLE
                    }
                    HolderState.SUCCESS -> {
                        ivDone.visibility = View.VISIBLE
                    }
                    HolderState.FAIL -> {
                        //ivAdded.visibility = View.GONE
                    }

                }
            }
        }
    }

    private fun hideButtons() {
        with(binding) {
            btnMainAction.visibility = View.GONE
            spinner.visibility = View.GONE
            ivDone.visibility = View.GONE
        }
    }

    private fun showNormalState() {
        with(binding) {
            btnMainAction.visibility = View.VISIBLE
            spinner.visibility = View.GONE
            ivDone.visibility = View.GONE
        }
    }

    open fun bind(userModel: UserModel) {
        binding.apply {
            with(userModel) {
                _currentUserModel = this
                tvName.text = email
                tvOccupation.text = id.toString()
                ivProfilePic.loadImage(pictureUri)
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
            binding.apply {
                btnMainAction.setOnClickListener {
                    onItemClicked()
                }
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

