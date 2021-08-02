package tk.quietdev.level1.ui.contacts.edit


import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import tk.quietdev.level1.databinding.FragmentEditProfileBinding
import tk.quietdev.level1.models.User
import tk.quietdev.level1.ui.contacts.ContactsSharedViewModel
import tk.quietdev.level1.utils.ext.loadImage


class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding
    private lateinit var sharedViewModel: ContactsSharedViewModel
    private val args: EditProfileFragmentArgs by navArgs()
    private lateinit var oldUserID: String
    private var currentUser: User? = null
    private var isLocalPicture = false
    private lateinit var localPictureUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentEditProfileBinding.inflate(inflater, container, false).apply {
            binding = this
            sharedViewModel = ViewModelProvider(requireActivity()).get(ContactsSharedViewModel::class.java)
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      /*  currentUser = sharedViewModel.getUser(args.email)
        currentUser?.let {
            oldUserID = it.email
            bindValues(it)
            setListeners()
        }*/


    }

    private fun setListeners() {
        binding.apply {
            btnSave.setOnClickListener {
                getNewValues()
            }
            btnAddPhoto.setOnClickListener {
                // FIXME: 7/31/2021 no idea how this works
                getAction.launch(
                    "image/"
                )
            }
        }

    }

    private fun getNewValues() {
        currentUser?.let {
            binding.apply {
                val newUser = it.copy(
                    userName = etName.text.toString(),
                    email = etEmail.text.toString(),
                    occupation = etOccupation.text.toString(),
                    physicalAddress = etAddress.text.toString(),
                    birthDate = etBirthDate.text.toString(),
                    phone = etPhoneNumber.text.toString(),
                    picture = if (isLocalPicture) localPictureUri.toString() else it.picture
                )
                // I have to set the currentUser to new user, so if fields does not change on a next button press
                // im not calling to update
            /*    if (currentUser != newUser) {
                    sharedViewModel.updateUser(oldUserID, newUser)
                    findNavController().previousBackStackEntry?.savedStateHandle?.set(Const.EDITUSER_GET_BACK, newUser.email)
                    currentUser = newUser
                }*/
            }
        }

    }

    private fun bindValues(user: User) {
        binding.apply {
            ivProfilePic.loadImage(user.picture)
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
        it.let {
            localPictureUri = it
            binding.ivProfilePic.loadImage(it)
            isLocalPicture = true
        }
    }




}