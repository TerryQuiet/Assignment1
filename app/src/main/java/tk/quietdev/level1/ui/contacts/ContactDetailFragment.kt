package tk.quietdev.level1.ui.contacts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import tk.quietdev.level1.databinding.FragmentContactDetailBinding
import tk.quietdev.level1.databinding.UserDetailBinding
import tk.quietdev.level1.models.User
import tk.quietdev.level1.utils.Const
import tk.quietdev.level1.utils.ext.loadImage


class ContactDetailFragment : Fragment() {

    private lateinit var binding: FragmentContactDetailBinding
    private lateinit var userDetailBinding: UserDetailBinding
    private lateinit var viewModel: ContactsViewModel

    private val args: ContactDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentContactDetailBinding.inflate(inflater, container, false).apply {
            binding = this
            userDetailBinding = binding.topContainer
            viewModel = ViewModelProvider(requireActivity()).get(ContactsViewModel::class.java)
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // FIXME: 7/31/2021  
        val currentUser = viewModel.getUser(args.email)
        bind(currentUser)
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(Const.EDITUSER_GET_BACK)
            ?.observe(
                viewLifecycleOwner
            ) {
                bind(viewModel.getUser(it))
            }
    }


    private fun bind(currentUser: User?) {
        currentUser?.let {
            userDetailBinding.apply {
                tvName.text = it.userName
                tvAddress.text = it.physicalAddress
                tvOccupation.text = it.occupation
                ivProfilePic.loadImage(it.picture)
            }
            binding.btnEditProfile.setOnClickListener {
                openEditFragment(currentUser.email)
            }
        }
    }

    private fun openEditFragment(email: String) {
        findNavController().navigate(
            ContactDetailFragmentDirections.actionContactDetailFragmentToEditProfileFragment(
                email
            )
        )
    }
}