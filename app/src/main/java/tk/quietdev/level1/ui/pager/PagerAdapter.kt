package tk.quietdev.level1.ui.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commitNow
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import tk.quietdev.level1.ui.pager.contacts.list.ContactsListFragment
import tk.quietdev.level1.ui.pager.navhost.ListNavHostFragment
import tk.quietdev.level1.ui.pager.navhost.SettingsNavHostFragment
import tk.quietdev.level1.ui.pager.settings.SettingsFragment

class PagerAdapter(fm: FragmentManager, lf: Lifecycle) :
        FragmentStateAdapter(fm, lf) {

        enum class Pages(val position: Int) {
            LIST(1),
            SETTINGS(0)
        }

        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                1 -> ListNavHostFragment()
                else -> SettingsNavHostFragment()
            }
        }



    }