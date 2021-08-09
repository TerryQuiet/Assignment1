package tk.quietdev.level1.ui.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import tk.quietdev.level1.databinding.FragmentContactPagerBinding
import tk.quietdev.level1.ui.contacts.list.ContactsListFragment
import tk.quietdev.level1.ui.settings.SettingsFragment


class FragmentContactPager : Fragment() {

    private lateinit var binding: FragmentContactPagerBinding
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentContactPagerBinding.inflate(inflater, container, false).apply {
            binding = this
            viewPager = binding.pager
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager.adapter = ScreenSlidePagerAdapter(this)
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private inner class ScreenSlidePagerAdapter(fa: FragmentContactPager) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                1 -> ContactsListFragment()
                else -> SettingsFragment()
            }

        }
    }



}

