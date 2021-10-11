package tk.quietdev.level1.ui.main.myprofile.contacts.list.adapter

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ListAdapter
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.ui.main.myprofile.contacts.list.adapter.holders.ContactHolderParent
import tk.quietdev.level1.ui.main.myprofile.contacts.list.adapter.holders.HolderState
import tk.quietdev.level1.utils.DiffCallBack

abstract class BaseContactsAdapter(
    protected val onClickListener: ContactHolderParent.OnItemClickListener,
    private val holderState: MutableLiveData<MutableMap<Int, HolderState>>,
) : ListAdapter<UserModel, ContactHolderParent>(DiffCallBack) {
    override fun onViewAttachedToWindow(holder: ContactHolderParent) {
        super.onViewAttachedToWindow(holder)
        holder.setHolderStateObserver(holderState)
    }

    override fun onBindViewHolder(holder: ContactHolderParent, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onViewDetachedFromWindow(holder: ContactHolderParent) {
        super.onViewDetachedFromWindow(holder)
        holder.removeHolderStateObserver(holderState)
    }
}