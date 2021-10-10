package tk.quietdev.level1.ui.main.myprofile.contacts.list.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import tk.quietdev.level1.databinding.ListItemBinding
import tk.quietdev.level1.ui.main.myprofile.contacts.list.adapter.holders.ContactHolderRemove
import tk.quietdev.level1.ui.main.myprofile.contacts.list.adapter.holders.HolderState
import tk.quietdev.level1.ui.main.myprofile.contacts.list.adapter.holders.ParentContactHolder
import tk.quietdev.level1.utils.OnSwipeCallBack

class RemoveContactsAdapter(
    private val icon: Drawable?,
    onClickListener: ParentContactHolder.OnItemClickListener,
    val removeState: MutableLiveData<List<Int>>,
    holderState: MutableLiveData<MutableMap<Int, HolderState>>,

    ) : ParentContactsAdapter(onClickListener, holderState),
    OnSwipeCallBack.Listener {

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val onSwipeCallBack = OnSwipeCallBack(this)
        ItemTouchHelper(onSwipeCallBack).attachToRecyclerView(recyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentContactHolder {
        return ContactHolderRemove(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ).apply {
                btnMainAction.icon = icon
            },
            onClickListener
        )
    }

    override fun onHolderSwiped(viewHolder: RecyclerView.ViewHolder) {
        (viewHolder as ContactHolderRemove).onIconClick()
    }

    override fun onViewAttachedToWindow(holder: ParentContactHolder) {
        super.onViewAttachedToWindow(holder)
        holder as ContactHolderRemove
        holder.setListObserver(removeState)
    }

    override fun onViewDetachedFromWindow(holder: ParentContactHolder) {
        super.onViewDetachedFromWindow(holder)
        holder as ContactHolderRemove
        holder.removeListObserver(removeState)
    }

}

