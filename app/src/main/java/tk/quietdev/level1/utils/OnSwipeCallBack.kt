package tk.quietdev.level1.utils

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView


class OnSwipeCallBack(
    private val call: Listener) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        call.swipedOn(viewHolder)
    }


    interface Listener {
      fun swipedOn(viewHolder: RecyclerView.ViewHolder)
    }
}