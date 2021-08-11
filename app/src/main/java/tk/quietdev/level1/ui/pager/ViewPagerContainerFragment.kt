package tk.quietdev.level1.ui.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import tk.quietdev.level1.databinding.FragmentViewpagerContainerBinding

class ViewPagerContainerFragment : Fragment() {

    private lateinit var binding: FragmentViewpagerContainerBinding
    private val appbarViewModel: AppbarViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewpagerContainerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.adapter =
            PagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle, ::changePage)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                1 -> "List"
                else -> "Setting"
            }
        }.attach()

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                appbarViewModel.currentNavController.value =
                    appbarViewModel.currentNavController.value
            }
        })

    }

    fun changePage(page: Int) {
        binding.viewPager.setCurrentItem(page, true)
    }

    override fun onDestroyView() {

        val viewPager2 = binding.viewPager

        /*
            Without setting ViewPager2 Adapter it causes memory leak

            https://stackoverflow.com/questions/62851425/viewpager2-inside-a-fragment-leaks-after-replacing-the-fragment-its-in-by-navig
         */
        viewPager2.adapter = null

        super.onDestroyView()
    }
}