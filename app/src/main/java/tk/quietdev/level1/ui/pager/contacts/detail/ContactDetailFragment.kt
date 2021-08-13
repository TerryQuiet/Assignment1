package tk.quietdev.level1.ui.pager.contacts.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
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

    private var _binding: FragmentContactDetailBinding? = null
    private val binding get() = _binding!!
    private var _userDetailBinding: UserDetailBinding? = null
    private val userDetailBinding get() = _userDetailBinding!!

    private val sharedViewModel: ContactsSharedViewModel by sharedViewModel()
    private val viewModel : ContactDetailViewModel by viewModel()
    private val args: ContactDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity).supportActionBar?.show()
        return FragmentContactDetailBinding.inflate(inflater, container, false).apply {
            _binding = this
            _userDetailBinding = binding.topContainer
        }.root
    }

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

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _userDetailBinding = null
    }

}