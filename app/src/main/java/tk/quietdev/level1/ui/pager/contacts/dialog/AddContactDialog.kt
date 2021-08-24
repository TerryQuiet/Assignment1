package tk.quietdev.level1.ui.pager.contacts.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import tk.quietdev.level1.databinding.DialogAddContactBinding
import tk.quietdev.level1.ui.pager.contacts.ContactsSharedViewModel

@AndroidEntryPoint
class AddContactDialog
 : DialogFragment() {

    private lateinit var binding: DialogAddContactBinding
    private val viewModel: AddContactViewModel by viewModels()
    private val sharedViewModel: ContactsSharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObserver()
        setListeners()
    }

    private fun setListeners() {
        binding.apply {

            bntCancel.setOnClickListener {
                dismiss()
            }

            btnAdd.setOnClickListener {
                viewModel.addNewUser(
                    etName.text.toString(),
                    etSurname.text.toString(),
                    etOccupation.text.toString()
                )
                dismiss()
            }

        }

    }

    private fun setObserver() {
        viewModel.newUser.observe(viewLifecycleOwner) { user ->
            user?.let { sharedViewModel.newUser.value = user }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        DialogAddContactBinding.inflate(LayoutInflater.from(context)).apply { binding = this }.root


    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


}