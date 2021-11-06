package tk.quietdev.level1.ui.main.myprofile.contacts.pager

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import tk.quietdev.level1.databinding.FragmentViewpagerContainerBinding
import tk.quietdev.level1.domain.models.UserModel
import tk.quietdev.level1.ui.base.BaseFragment
import javax.inject.Inject


@AndroidEntryPoint
class ViewPagerContainerFragment :
    BaseFragment<FragmentViewpagerContainerBinding>(FragmentViewpagerContainerBinding::inflate) {

    @Inject
    lateinit var assistedFactory: ParentPagerViewModelFactory

    private val args: ViewPagerContainerFragmentArgs by navArgs()
    private val viewModel: ParentPagerViewModel by viewModels {
        ParentPagerViewModel.Factory(
            assistedFactory,
            args.userId,
            args.type
        )
    }

    override fun setListeners() {
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.currentPage = position
            }
        })
    }

    override fun setObservers() {
        viewModel.userListAll.observe(viewLifecycleOwner) {
            if (it.isNotEmpty())
                setupViewPager(it)
        }
    }


    private fun setupViewPager(list: List<UserModel>) {
        binding.viewPager.adapter =
            PagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle, list)
        binding.viewPager.setCurrentItem(viewModel.currentPage, false)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = list[position].email
        }.attach()
    }


}