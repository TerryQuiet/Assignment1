package tk.quietdev.level1.ui.pager.contacts.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import tk.quietdev.level1.databinding.FragmentContactDetailBinding
import tk.quietdev.level1.databinding.UserDetailBinding
import tk.quietdev.level1.models.User
import tk.quietdev.level1.ui.pager.contacts.ContactsSharedViewModel
import tk.quietdev.level1.utils.ext.loadImage


class ContactDetailFragment : Fragment() {

    private lateinit var binding: FragmentContactDetailBinding
    private lateinit var userDetailBinding: UserDetailBinding
    private val sharedViewModel: ContactsSharedViewModel by sharedViewModel()
    private val viewModel : ContactDetailViewModel by viewModel()
    private val args: ContactDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentContactDetailBinding.inflate(inflater, container, false).apply {
            binding = this
            userDetailBinding = binding.topContainer

        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currentUser = args.user
        bindViews()
        setObservers()
    }

    private fun setObservers() {
        sharedViewModel.updatedUser.observe(viewLifecycleOwner) {
            if (it != null) {
                viewModel.currentUser = it
                bindViews()
            }
        }
    }


    private fun bindViews() {
        viewModel.currentUser.let {
            userDetailBinding.apply {
                tvName.text = it.userName
                tvAddress.text = it.physicalAddress
                tvOccupation.text = it.occupation
                ivProfilePic.loadImage(it.pictureUri)
            }
            binding.btnEditProfile.setOnClickListener {
                openEditFragment(viewModel.currentUser)
            }
            binding.btnMessage.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun openEditFragment(user: User) {
        findNavController().navigate(
            ContactDetailFragmentDirections.actionContactDetailFragmentToEditProfileFragment(
               user
            )
        )
    }
}