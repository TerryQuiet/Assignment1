package tk.quietdev.level1.ui.pager.settings.edit


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import tk.quietdev.level1.databinding.FragmentEditProfileBinding
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.ui.pager.contacts.ContactsSharedViewModel
import tk.quietdev.level1.utils.ext.loadImage

@AndroidEntryPoint
class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: ContactsSharedViewModel by activityViewModels()
    private val viewModel: EditProfileViewModel by viewModels()
    private val args: EditProfileFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentEditProfileBinding.inflate(inflater, container, false).apply {
            (activity as AppCompatActivity).supportActionBar?.show()
            _binding = this
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.apply {
            currentUserModel = args.user
            bindValues(currentUserModel)
            setListeners()
        }


    }

    private fun setListeners() {
        binding.apply {
            btnSave.setOnClickListener {
                if (updateUser()) {
                    sharedViewModel.updatedUser.value = viewModel.currentUserModel
                }
                findNavController().popBackStack()
            }
            btnAddPhoto.setOnClickListener {
                getAction.launch(
                    "image/"
                )
            }
        }
    }

    /**
     * @return returns true if user was updated
     */
    private fun updateUser(): Boolean {
        binding.apply {
          return viewModel.updateUser(
                    userName = etName.text.toString(),
                    email = etEmail.text.toString(),
                    occupation = etOccupation.text.toString(),
                    physicalAddress = etAddress.text.toString(),
                    birthDate = etBirthDate.text.toString(),
                    phone = etPhoneNumber.text.toString(),
                    pictureUri =
                    if (viewModel.localPictureUri != null) viewModel.localPictureUri.toString() else viewModel.currentUserModel.pictureUri
                )
        }
    }

    private fun bindValues(userModel: UserModel) {
        binding.apply {
            ivProfilePic.loadImage(userModel.pictureUri)
            etAddress.setText(userModel.physicalAddress)
            etOccupation.setText(userModel.occupation)
            etEmail.setText(userModel.email)
            etBirthDate.setText(userModel.birthDate)
            etPhoneNumber.setText(userModel.phone)
            etName.setText(userModel.userName)
        }
    }


    private val getAction = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        viewModel.apply {
            localPictureUri = it
            binding.ivProfilePic.loadImage(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}