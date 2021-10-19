package tk.quietdev.level1.ui.main.myprofile.contacts.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import tk.quietdev.level1.BaseFragment
import tk.quietdev.level1.databinding.FragmentContactDetailBinding
import tk.quietdev.level1.utils.Const
import tk.quietdev.level1.utils.ext.loadImage

@AndroidEntryPoint
class ContactDetailFragment :
    BaseFragment<FragmentContactDetailBinding>(FragmentContactDetailBinding::inflate) {
    private val viewModel: ContactDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.show()
        arguments?.takeIf { it.containsKey(Const.CONTACT_DETAIL) }?.apply {
            viewModel.currentUserModelId = getInt(Const.CONTACT_DETAIL)!!
        }
        // TODO: 10/17/2021 viewModelFactory 
        bindViews()
    }

    private fun bindViews() {
        viewModel.currentUserModel.let {
            binding.topContainer.apply {
                tvName.text = it.userName
                tvAddress.text = it.physicalAddress
                tvOccupation.text = it.occupation
                ivProfilePic.loadImage(it.pictureUri)
            }
        }
    }

}