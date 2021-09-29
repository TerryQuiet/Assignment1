package tk.quietdev.level1.ui.main.myprofile.edit


import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import tk.quietdev.level1.BaseFragment
import tk.quietdev.level1.R
import tk.quietdev.level1.databinding.FragmentEditProfileBinding
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.utils.CalendarUtil
import tk.quietdev.level1.utils.Const
import tk.quietdev.level1.utils.Resource
import tk.quietdev.level1.utils.ext.loadImage

@AndroidEntryPoint
class EditProfileFragment :
    BaseFragment<FragmentEditProfileBinding>(FragmentEditProfileBinding::inflate) {

    private val viewModel: EditProfileViewModel by viewModels()
    private val datePicker by lazy {
        MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.select_date))
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setCalendarConstraints(CalendarUtil.getConstrains().build())
            .build()
    }

    override fun setObservers() {
        viewModel.currentUserModel.observe(viewLifecycleOwner) { resource ->
            binding.apply {
                when (resource) {
                    is Resource.Success<UserModel> -> {
                        progressCircular.visibility = View.GONE
                        if (resource.message == Const.ON_USER_UPDATE) {
                            findNavController().navigateUp()
                        } else {
                            resource.data?.let { userModel ->
                                bindValues(userModel)
                            }
                        }
                    }
                    is Resource.Error -> {
                        progressCircular.visibility = View.GONE
                    }
                    is Resource.Loading -> {
                        progressCircular.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun setListeners() {
        binding.apply {
            btnSave.setOnClickListener {
                updateUser()
            }
            btnAddPhoto.setOnClickListener {
                getAction.launch(
                    "image/"
                )
            }
            datePicker.addOnPositiveButtonClickListener {
                datePicker.selection?.let {
                    etBirthDate.setText(viewModel.getShortDate(it))
                }
            }
        }

    }

    private fun updateUser() {
        binding.apply {
            progressCircular.visibility = View.VISIBLE
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
            etBirthDateParent.setEndIconOnClickListener {
                datePicker.show(childFragmentManager, "tag")
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

}