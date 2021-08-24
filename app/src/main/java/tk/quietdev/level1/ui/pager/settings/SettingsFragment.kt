package tk.quietdev.level1.ui.pager.settings


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import tk.quietdev.level1.databinding.FragmentSettingsBinding
import tk.quietdev.level1.databinding.UserDetailBinding
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.utils.Const
import tk.quietdev.level1.utils.ext.loadImage

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var userDetailBinding: UserDetailBinding
    private val viewModel: SettingsViewModel by viewModels()
    private val settingsSharedViewModel :SettingsSharedViewModel by activityViewModels()

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
        val user = activity?.intent?.getParcelableExtra<UserModel>(Const.USER)
        user?.let {
            viewModel.currentUserModel = it
            bindListeners()
            bindValues()
        }
    }

    private fun bindListeners() {
        binding.apply {
            btnViewContacts.setOnClickListener {
               settingsSharedViewModel.buttonClicked.value = true
            }
            btnEditProfile.setOnClickListener {

            }
        }
    }

    private fun bindValues() {
        val currentUser = viewModel.currentUserModel
        binding.topContainer.apply {
            tvName.text = currentUser.userName
            tvAddress.text = currentUser.physicalAddress
            tvOccupation.text = currentUser.occupation
            ivProfilePic.loadImage(currentUser.pictureUri)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}