package tk.quietdev.level1.ui.main.myprofile.contacts.pager

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import tk.quietdev.level1.databinding.FragmentViewpagerContainerBinding
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.ui.main.AppbarSharedViewModel


@AndroidEntryPoint
class ViewPagerContainerFragment : Fragment() {

    private var _binding: FragmentViewpagerContainerBinding? = null
    private val binding get() = _binding!!
    private val appbarSharedViewModel: AppbarSharedViewModel by activityViewModels()
    private val viewModel: PagerViewModel by viewModels()
    private val args: ViewPagerContainerFragmentArgs by navArgs()


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
        if (viewModel.currentPage == -1) {
            when (args.type) {
                ViewPagerType.CONTACTS -> viewModel.initCurrentUserContacts(args.userId)
                ViewPagerType.ALL_USERS -> viewModel.initAllUsers(args.userId)
            }

        }
        setObservers()
        setListeners()
    }

    private fun setListeners() {


        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.currentPage = position
            }
        })
    }


    private fun setupViewPager(list: List<UserModel>) {
        binding.viewPager.adapter =
            PagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle, list)

        Log.d("TAG", "setupViewPager: ${viewModel.currentPage}")

        binding.viewPager.setCurrentItem(viewModel.currentPage, false)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = list[position].email
        }.attach()


    }

    private fun setObservers() {

        viewModel.userListAll.observe(viewLifecycleOwner) {
            if (it.isNotEmpty())
                setupViewPager(it)
        }
    }

    override fun onDestroyView() {

        _binding = null
        super.onDestroyView()
    }




}