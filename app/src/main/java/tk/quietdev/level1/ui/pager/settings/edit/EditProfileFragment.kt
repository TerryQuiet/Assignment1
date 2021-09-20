package tk.quietdev.level1.ui.pager.settings.edit


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import tk.quietdev.level1.databinding.FragmentEditProfileBinding
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.utils.Const
import tk.quietdev.level1.utils.Resource
import tk.quietdev.level1.utils.ext.loadImage

@AndroidEntryPoint
class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditProfileViewModel by viewModels()

    val datePicker =
        MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentEditProfileBinding.inflate(inflater, container, false).apply {
            (activity as AppCompatActivity).supportActionBar?.show()
            _binding = this
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setObservers()
    }





    private fun setObservers() {
        viewModel.currentUserModel.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success<UserModel> -> {
                    binding.progressCircular.visibility = View.GONE
                    if (it.message == Const.ON_USER_UPDATE) {
                        findNavController().popBackStack()
                    } else {
                        it.data?.let { userModel ->
                            bindValues(userModel)
                        }
                    }
                }
                is Resource.Error -> {
                    binding.progressCircular.visibility = View.GONE
                }
                is Resource.Loading -> {
                    binding.progressCircular.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setListeners() {
        binding.apply {
            btnSave.setOnClickListener {
                updateUser()
            }
            btnAddPhoto.setOnClickListener {
                getAction.launch(
                    "image/"
                )
            }
        }
        datePicker.addOnPositiveButtonClickListener {
            datePicker.selection?.let {
                binding.etBirthDate.setText(viewModel.getShortDate(it))
            }

        }
    }

    private fun updateUser() {
        binding.progressCircular.visibility = View.VISIBLE
        binding.apply {
            viewModel.updateUser(
                userName = etName.text.toString(),
                email = etEmail.text.toString(),
                occupation = etOccupation.text.toString(),
                physicalAddress = etAddress.text.toString(),
                birthDate = etBirthDate.text.toString(),
                phone = etPhoneNumber.text.toString(),
                // pictureUri = if (viewModel.localPictureUri != null) viewModel.localPictureUri.toString() else viewModel.currentUserModel.value.data.pictureUri
            )
        }
    }

    private fun bindValues(userModel: UserModel) {
        binding.apply {
            ivProfilePic.loadImage(userModel.pictureUri)
            etAddress.setText(userModel.physicalAddress)
            etOccupation.setText(userModel.occupation)
            etEmail.setText(userModel.email)
            etBirthDate.setText(userModel.birthDate)
            etBirthDate.setOnFocusChangeListener { _, focused ->
                if (focused) {
                    datePicker.show(childFragmentManager, "tag")
                }
            }
            etPhoneNumber.setText(userModel.phone)
            etName.setText(userModel.userName)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}