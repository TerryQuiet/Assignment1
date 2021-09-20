package tk.quietdev.level1.ui.main.myprofile.contacts.pager

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.ui.main.myprofile.contacts.detail.ContactDetailFragment
import tk.quietdev.level1.utils.Const


class PagerAdapter(fm: FragmentManager, lf: Lifecycle,private val userListAll: List<UserModel>?) :
    FragmentStateAdapter(fm, lf) {

    enum class Pages(val position: Int) {
        LIST(1),
        SETTINGS(0)
    }

    fun test() {

    }

    override fun getItemCount(): Int = userListAll?.size ?: 0

    override fun createFragment(position: Int): Fragment {
        Log.d("TAG", "createFragment: $position")
        val fragment = ContactDetailFragment()
        val userModel = userListAll?.get(position)
        userModel?.let {
            fragment.arguments = Bundle().apply {
                putParcelable(Const.CONTACT_DETAIL, it )
            }
        }

        return fragment
    }

}