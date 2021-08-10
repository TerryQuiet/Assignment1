package tk.quietdev.level1.ui.pager.contacts.edit


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import tk.quietdev.level1.databinding.FragmentEditProfileBinding
import tk.quietdev.level1.models.User
import tk.quietdev.level1.ui.pager.contacts.ContactsSharedViewModel
import tk.quietdev.level1.utils.ext.loadImage

class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding
    private val sharedViewModel: ContactsSharedViewModel by sharedViewModel()
    private val viewModel: EditProfileViewModel by viewModel()
    private val args: EditProfileFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentEditProfileBinding.inflate(inflater, container, false).apply {
            (activity as AppCompatActivity).supportActionBar?.show()
            binding = this
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.apply {
            currentUser = args.user
            bindValues(currentUser)
            setListeners()
        }


    }

    private fun setListeners() {
        binding.apply {
            btnSave.setOnClickListener {
                if (updateUser()) {
                    sharedViewModel.updatedUser.value = viewModel.currentUser
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
                    if (viewModel.localPictureUri != null) viewModel.localPictureUri.toString() else viewModel.currentUser.pictureUri
                )
        }
    }

    private fun bindValues(user: User) {
        binding.apply {
            ivProfilePic.loadImage(user.pictureUri)
            etAddress.setText(user.physicalAddress)
            etOccupation.setText(user.occupation)
            etEmail.setText(user.email)
            etBirthDate.setText(user.birthDate)
            etPhoneNumber.setText(user.phone)
            etName.setText(user.userName)
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
}