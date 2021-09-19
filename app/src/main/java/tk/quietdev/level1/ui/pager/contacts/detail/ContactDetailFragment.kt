package tk.quietdev.level1.ui.pager.contacts.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import tk.quietdev.level1.databinding.FragmentContactDetailBinding
import tk.quietdev.level1.databinding.UserDetailBinding
import tk.quietdev.level1.utils.ext.loadImage

@AndroidEntryPoint
class ContactDetailFragment : Fragment() {

    private var _binding: FragmentContactDetailBinding? = null
    private val binding get() = _binding!!
    private var _userDetailBinding: UserDetailBinding? = null
    private val userDetailBinding get() = _userDetailBinding!!
    private val viewModel: ContactDetailViewModel by viewModels()
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
        viewModel.currentUserModel = args.user
        bindViews()

    }

    private fun bindViews() {
        viewModel.currentUserModel.let {
            userDetailBinding.apply {
                tvName.text = it.userName
                tvAddress.text = it.physicalAddress
                tvOccupation.text = it.occupation
                ivProfilePic.loadImage(it.pictureUri)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _userDetailBinding = null
    }

}