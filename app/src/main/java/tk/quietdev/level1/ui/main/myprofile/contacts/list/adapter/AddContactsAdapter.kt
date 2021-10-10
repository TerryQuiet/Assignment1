package tk.quietdev.level1.ui.main.myprofile.contacts.list.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import tk.quietdev.level1.databinding.ListItemBinding
import tk.quietdev.level1.ui.main.myprofile.contacts.list.adapter.holders.ContactHolderAdd
import tk.quietdev.level1.ui.main.myprofile.contacts.list.adapter.holders.HolderState
import tk.quietdev.level1.ui.main.myprofile.contacts.list.adapter.holders.ParentContactHolder

class AddContactsAdapter(
    private val icon: Drawable?,
    onClickListener: ParentContactHolder.OnItemClickListener,
    holderState: MutableLiveData<MutableMap<Int, HolderState>>
) : ParentContactsAdapter(onClickListener, holderState) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentContactHolder {
        return ContactHolderAdd(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ).apply {
                btnMainAction.icon = icon
                btnMainAction.text = "Add"
            },
            onClickListener,
        )
    }
}