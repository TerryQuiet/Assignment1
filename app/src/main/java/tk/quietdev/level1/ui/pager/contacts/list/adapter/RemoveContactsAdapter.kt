package tk.quietdev.level1.ui.pager.contacts.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import tk.quietdev.level1.databinding.ListItemBinding
import tk.quietdev.level1.ui.pager.contacts.list.adapter.holders.ContactHolderBase
import tk.quietdev.level1.ui.pager.contacts.list.adapter.holders.ContactHolderRemove
import tk.quietdev.level1.utils.OnSwipeCallBack

class RemoveContactsAdapter(
    onClickListener: ContactHolderBase.OnItemClickListener,
    val removeState: MutableLiveData<List<Int>>,
    holderState: MutableLiveData<MutableMap<Int, HolderState>>,

    ) : BaseContactsAdapter(onClickListener, holderState),
    OnSwipeCallBack.Listener {

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val onSwipeCallBack = OnSwipeCallBack(this)
        ItemTouchHelper(onSwipeCallBack).attachToRecyclerView(recyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolderBase {
        return ContactHolderRemove(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            onClickListener
        )
    }

    override fun onHolderSwiped(viewHolder: RecyclerView.ViewHolder) {
        (viewHolder as ContactHolderRemove).onIconClick()
    }

    override fun onViewAttachedToWindow(holder: ContactHolderBase) {
        super.onViewAttachedToWindow(holder)
        holder as ContactHolderRemove
        holder.setListObserver(removeState)
    }

    override fun onViewDetachedFromWindow(holder: ContactHolderBase) {
        super.onViewDetachedFromWindow(holder)
        holder as ContactHolderRemove
        holder.removeListObserver(removeState)
    }

}

