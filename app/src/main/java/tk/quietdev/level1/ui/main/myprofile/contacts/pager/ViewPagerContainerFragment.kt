package tk.quietdev.level1.ui.main.myprofile.contacts.pager

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import tk.quietdev.level1.databinding.FragmentViewpagerContainerBinding
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.ui.main.AppbarSharedViewModel
import tk.quietdev.level1.ui.main.myprofile.ProfileSharedViewModel

@AndroidEntryPoint
class ViewPagerContainerFragment : Fragment() {

    private var _binding: FragmentViewpagerContainerBinding? = null
    private val binding get() = _binding!!
    private val appbarSharedViewModel: AppbarSharedViewModel by activityViewModels()
    private val profileSharedViewModel: ProfileSharedViewModel by activityViewModels()
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
        setObservers()
        setListeners()
    }

    private fun setListeners() {
        binding.ivCross.setOnClickListener {
            appbarSharedViewModel.showSearchLayout(false)
        }

        /* binding.etSearch.doOnTextChanged { text, _, _, _ ->
             appbarSharedViewModel.searchText.value = text.toString()
         }*/ // TODO: 9/20/2021
    }

    private fun setupViewPager(list: List<UserModel>) {
        binding.viewPager.adapter =
            PagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle, list)

        var currentUserIndex = -1
        list.forEachIndexed { index, userModel ->
            if (userModel.id == args.userId) {
                currentUserIndex = index
                return@forEachIndexed
            }
        }

        Log.d("TAG", "setupViewPager: $currentUserIndex and userId = ${args.userId} and list \n $list")
        binding.viewPager.setCurrentItem(currentUserIndex, false)

          TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
              tab.text = list[position].email
          }.attach()
    }

    private fun setObservers() {
     /*   profileSharedViewModel.viewMyContactsButtonClicked.observe(viewLifecycleOwner) { buttonClicked ->
            if (buttonClicked) {
                binding.viewPager.currentItem = PagerAdapter.Pages.LIST.position
                profileSharedViewModel.viewMyContactsButtonClicked.value = false
            }
        }*/

        appbarSharedViewModel.searchLayoutVisibility.observe(viewLifecycleOwner) {
            binding.searchTopBar.visibility = it
        }

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