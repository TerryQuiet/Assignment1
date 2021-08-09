package tk.quietdev.level1.ui.pager

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import tk.quietdev.level1.R
import tk.quietdev.level1.databinding.ActivityPagerHolderBinding
import tk.quietdev.level1.ui.settings.SettingsFragment

class PagerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPagerHolderBinding
    lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var viewPager: ViewPager2
    lateinit var tb: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPagerHolderBinding.inflate(layoutInflater)
        viewPager = binding.pagerActivity
        setContentView(binding.root)
        setSupportActionBar(binding.tbContacts)
        tb = supportActionBar!!
        viewPager.adapter = ScreenSlidePagerAdapter(supportFragmentManager, lifecycle)


    }

    fun setupAppBar(nav: NavController) {
        appBarConfiguration = AppBarConfiguration(nav.graph)
        setupActionBarWithNavController(nav, appBarConfiguration)
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private inner class ScreenSlidePagerAdapter(fm: FragmentManager, lf: Lifecycle) :
        FragmentStateAdapter(fm, lf) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                1 -> FragmentNavGraph()
                else -> SettingsFragment()
            }
        }
    }

    override fun onSupportNavigateUp() =
        NavigationUI.navigateUp(findNavController(R.id.nav_host), appBarConfiguration)

}