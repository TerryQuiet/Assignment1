package tk.quietdev.level1.ui.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import tk.quietdev.level1.databinding.FragmentEditProfileBinding
import tk.quietdev.level1.models.User
import tk.quietdev.level1.utils.ext.loadImage

class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding
    private lateinit var viewModel: ContactsViewModel
    private val args: ContactDetailFragmentArgs by navArgs()
    private val user = User("", "")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentEditProfileBinding.inflate(inflater, container, false).apply {
            binding = this
            viewModel = ViewModelProvider(requireActivity()).get(ContactsViewModel::class.java)
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = viewModel.getUser(args.email)
        user?.let {
            bindValues(it)
            setListeners()
        }
    }

    private fun setListeners() {
        binding.apply {
            btnSave.setOnClickListener {
                val user = User(
                    userName = etName.text.toString(),
                    email = etEmail.text.toString(),
                    occupation = etOccupation.text.toString(),
                    physicalAddress = etAddress.text.toString(),
                    birthDate = etBirthDate.text.toString(),
                    phone = etPhoneNumber.text.toString()
                )
                viewModel.updateUser(binding.etEmail.text.toString(), user)
                bindValues(user)
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

}