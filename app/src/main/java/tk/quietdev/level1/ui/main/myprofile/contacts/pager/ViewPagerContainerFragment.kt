package tk.quietdev.level1.ui.main.myprofile.contacts.pager

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import tk.quietdev.level1.BaseFragment
import tk.quietdev.level1.databinding.FragmentViewpagerContainerBinding
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.ui.main.AppbarSharedViewModel
import javax.inject.Inject


@AndroidEntryPoint
class ViewPagerContainerFragment :
    BaseFragment<FragmentViewpagerContainerBinding>(FragmentViewpagerContainerBinding::inflate) {

    private val appbarSharedViewModel: AppbarSharedViewModel by activityViewModels()
    @Inject
    lateinit var assistedFactory: ParentPagerViewModelFactory
    private val viewModel: ParentPagerViewModel by viewModels {
        ParentPagerViewModel.Factory(
            assistedFactory,
            "s"
        )
    }
    private val args: ViewPagerContainerFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel.currentPage == -1) {
            when (args.type) {
                ViewPagerType.CONTACTS -> {
                    appbarSharedViewModel.appBarLabel.value = "My contacts"
                    viewModel.initCurrentUserContacts(args.userId)
                }
                ViewPagerType.ALL_USERS -> {
                    appbarSharedViewModel.appBarLabel.value = "All users"
                    viewModel.initAllUsers(args.userId)
                }
            }
        }
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