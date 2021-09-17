package tk.quietdev.level1.ui.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import tk.quietdev.level1.databinding.FragmentViewpagerContainerBinding
import tk.quietdev.level1.ui.pager.settings.SettingsSharedViewModel

@AndroidEntryPoint
class ViewPagerContainerFragment : Fragment() {

    private var _binding: FragmentViewpagerContainerBinding? = null
    private val binding get() = _binding!!
    private val appbarSharedViewModel: AppbarSharedViewModel by activityViewModels()
    private val settingsSharedViewModel : SettingsSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewpagerContainerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        setObservers()
    }

    private fun setupViewPager() {
        binding.viewPager.adapter =
            PagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                1 -> "List"
                else -> "Setting"
            }
        }.attach()
    }

    private fun setObservers() {

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                appbarSharedViewModel.currentNavController.value =
                    appbarSharedViewModel.currentNavController.value
            }
        })

        settingsSharedViewModel.viewMyContactsButtonClicked.observe(viewLifecycleOwner) { buttonClicked ->
            if (buttonClicked) {
                binding.viewPager.currentItem = PagerAdapter.Pages.LIST.position
                settingsSharedViewModel.viewMyContactsButtonClicked.value = false
            }
        }

        appbarSharedViewModel.isSearchIconClicked.observe(viewLifecycleOwner) {
            if (it) {
                appbarSharedViewModel.navBarVisibility.value = View.GONE
                binding.searchTopBar.visibility = View.VISIBLE
            }
        }



    }

    override fun onDestroyView() {

        val viewPager2 = binding.viewPager
        /*
            Without setting ViewPager2 Adapter it causes memory leak
            https://stackoverflow.com/questions/62851425/viewpager2-inside-a-fragment-leaks-after-replacing-the-fragment-its-in-by-navig
         */
        viewPager2.adapter = null
        _binding = null

        super.onDestroyView()
    }


}