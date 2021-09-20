package tk.quietdev.level1.ui.pager.settings


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import tk.quietdev.level1.databinding.FragmentSettingsBinding
import tk.quietdev.level1.databinding.UserDetailBinding
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.ui.pager.AppbarSharedViewModel
import tk.quietdev.level1.ui.pager.ViewPagerContainerFragmentDirections
import tk.quietdev.level1.utils.ext.loadImage

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var userDetailBinding: UserDetailBinding
    private val viewModel: SettingsViewModel by viewModels()
    private val settingsSharedViewModel: SettingsSharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentSettingsBinding.inflate(inflater, container, false).apply {
            _binding = this
            userDetailBinding = binding.topContainer
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindListeners()
        setObservers()
    }

    private fun bindListeners() {
        binding.apply {
            btnViewContacts.setOnClickListener {
                settingsSharedViewModel.viewMyContactsButtonClicked.value = true
            }
            btnEditProfile.setOnClickListener {
                viewModel.currentUserModel.value?.apply {
                    this.data?.let {
                        openEditFragment(it)
                    }
                }

            }
            containerSocialButtons.iBtnFacebook.setOnClickListener {

            }
        }
    }

    private fun openEditFragment(userModel: UserModel) {
        findNavController().navigate(
            ViewPagerContainerFragmentDirections.actionPagerDestToEditProfileFragment(
                userModel
            )
        )
    }

    private fun bindViews(userModel: UserModel) {
        userModel.apply {
            Log.d("TAG", "bindViews: ${userModel}")
            binding.topContainer.apply {
                tvName.text = email
                tvAddress.text = physicalAddress
                tvOccupation.text = occupation
                ivProfilePic.loadImage(pictureUri)
            }
        }
    }

    private fun setObservers() {
        viewModel.currentUserModel.observe(viewLifecycleOwner) {
            it.data?.let { currentUser ->
                bindViews(currentUser)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}