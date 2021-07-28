package tk.quietdev.level1.ui.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import tk.quietdev.level1.database.FakeDatabase
import tk.quietdev.level1.databinding.FragmentContactDetailBinding
import tk.quietdev.level1.databinding.UserDetailBinding
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



        //val currentUser = viewModel.getUser(args.email)

     /*   userDetailBinding.apply {
            tvName.text = currentUser?.userName
            tvAddress.text = currentUser?.physicalAddress
            tvOccupation.text = currentUser?.occupation
            ivProfilePic.loadImage(currentUser?.picture)
        }*/


    }

}