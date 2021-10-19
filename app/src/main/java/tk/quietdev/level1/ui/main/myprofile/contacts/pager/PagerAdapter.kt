package tk.quietdev.level1.ui.main.myprofile.contacts.pager

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import tk.quietdev.level1.domain.models.UserModel
import tk.quietdev.level1.ui.main.myprofile.contacts.detail.ContactDetailFragment
import tk.quietdev.level1.utils.Const


class PagerAdapter(fm: FragmentManager, lf: Lifecycle,private val userListAll: List<UserModel>?) :
    FragmentStateAdapter(fm, lf) {

    override fun getItemCount(): Int = userListAll?.size ?: 0

    override fun createFragment(position: Int): Fragment {
        val fragment = ContactDetailFragment()
        val userModel = userListAll?.get(position)
        userModel?.let {
            fragment.arguments = Bundle().apply {
                putInt(Const.CONTACT_DETAIL, it.id)
            }
        }

        return fragment
    }

}