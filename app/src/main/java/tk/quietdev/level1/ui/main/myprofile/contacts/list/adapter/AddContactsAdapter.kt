package tk.quietdev.level1.ui.main.myprofile.contacts.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import tk.quietdev.level1.databinding.ListItemNewBinding
import tk.quietdev.level1.ui.main.myprofile.contacts.list.adapter.holders.ContactHolderAdd
import tk.quietdev.level1.ui.main.myprofile.contacts.list.adapter.holders.ContactHolderParent
import tk.quietdev.level1.ui.main.myprofile.contacts.list.adapter.holders.HolderState


class AddContactsAdapter(
    onClickListener: ContactHolderParent.OnItemClickListener,
    holderState: MutableLiveData<MutableMap<Int, HolderState>>
) : BaseContactsAdapter(onClickListener, holderState) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolderParent {
        return ContactHolderAdd(
            ListItemNewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            onClickListener,
        )
    }
}