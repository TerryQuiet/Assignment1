package tk.quietdev.level1.ui.pager.contacts.list.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tk.quietdev.level1.databinding.ListItemBinding
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.utils.DiffCallBack
import tk.quietdev.level1.utils.ListState
import tk.quietdev.level1.utils.OnSwipeCallBack

open class ContactsAdapter<T : ContactHolderBase>(
    private val removeState: MutableLiveData<ListState>,
    private val holderType: ContactHolderBase.HolderType,
    private val holder: T
) : ListAdapter<UserModel, T>(DiffCallBack), OnSwipeCallBack.Listener {


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        if (holderType == ContactHolderBase.HolderType.REMOVE) {
            val onSwipeCallBack = OnSwipeCallBack(this)
            ItemTouchHelper(onSwipeCallBack).attachToRecyclerView(recyclerView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T {

        holder.binding = ListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return holder
    }

    override fun onBindViewHolder(holder: T, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onHolderSwiped(viewHolder: RecyclerView.ViewHolder) {
        (viewHolder as ContactHolder).onIconClick()
    }

    override fun onViewAttachedToWindow(holder: T) {
        super.onViewAttachedToWindow(holder)
        if (holder is ContactHolder)
            holder.setObserver(removeState)
    }

    override fun onViewDetachedFromWindow(holder: T) {
        super.onViewDetachedFromWindow(holder)
        if (holder is ContactHolder)
            holder.removeObserver(removeState)
    }


}

