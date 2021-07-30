package tk.quietdev.level1.ui.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import tk.quietdev.level1.databinding.FragmentEditProfileBinding
import tk.quietdev.level1.utils.ext.loadImage

class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding
    private lateinit var viewModel: ContactsViewModel
    private val args: ContactDetailFragmentArgs by navArgs()

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
        bindValues()
    }

    private fun bindValues() {
        val user = viewModel.getUser(args.email)
        user?.let {
            binding.apply {
                ivProfilePic.loadImage(it.picture)
                etAddress.setText(it.physicalAddress)
                etOccupation.setText(it.occupation)
                etEmail.setText(it.email)
                etBirthDate.setText(it.birthDate)
                etPhoneNumber.setText(it.phone)
                etName.setText(it.userName)
            }
        }
    }

}