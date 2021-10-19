package tk.quietdev.level1.ui.main.myprofile.contacts.list.adapter

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ListAdapter
import tk.quietdev.level1.domain.models.UserModel
import tk.quietdev.level1.ui.main.myprofile.contacts.list.adapter.holders.HolderState
import tk.quietdev.level1.ui.main.myprofile.contacts.list.adapter.holders.ParentContactHolder
import tk.quietdev.level1.utils.DiffCallBack

abstract class ParentContactsAdapter(
    protected val onClickListener: ParentContactHolder.OnItemClickListener,
    private val holderState: MutableLiveData<MutableMap<Int, HolderState>>,
) : ListAdapter<UserModel, ParentContactHolder>(DiffCallBack) {
    override fun onViewAttachedToWindow(holder: ParentContactHolder) {
        super.onViewAttachedToWindow(holder)
        holder.setHolderStateObserver(holderState)
    }

    override fun onBindViewHolder(holder: ParentContactHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onViewDetachedFromWindow(holder: ParentContactHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.removeHolderStateObserver(holderState)
    }
}