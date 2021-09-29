package tk.quietdev.level1.ui.main.myprofile


import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import tk.quietdev.level1.BaseFragment
import tk.quietdev.level1.databinding.FragmentProfileBinding
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.utils.ext.loadImage

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private val viewModel: ProfileViewModel by viewModels()

    override fun setObservers() {
        viewModel.currentUserModel.observe(viewLifecycleOwner) {
            it.data?.let { currentUser ->
                bindViews(currentUser)
            }
        }
    }

    override fun setListeners() {
        binding.apply {
            btnViewContacts.setOnClickListener {
                findNavController().navigate(
                    ProfileFragmentDirections.actionProfileFragmentToRemoveContactsListFragment()
                )
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

    private fun bindViews(userModel: UserModel) {
        userModel.apply {
            binding.topContainer.apply {
                tvName.text = email
                tvAddress.text = physicalAddress
                tvOccupation.text = occupation
                ivProfilePic.loadImage(pictureUri)
            }
        }
    }

    private fun openEditFragment(userModel: UserModel) {
        findNavController().navigate(
            ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment(userModel)
        )
    }





}