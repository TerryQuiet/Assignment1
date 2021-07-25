package tk.quietdev.level1.ui.contacts

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import tk.quietdev.level1.databinding.DialogAddContactBinding

class AddContactDialog(private val viewModel: ContactsViewModel) : DialogFragment() {

    private var _binding: DialogAddContactBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogAddContactBinding.inflate(LayoutInflater.from(context))

        binding.bntCancel.setOnClickListener {
            dismiss()
        }

        binding.btnAdd.setOnClickListener {
            viewModel.onDialogAddClicked(binding)
            dismiss()
        }

        return AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .create()
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface Listener {
        fun onDialogAddClicked(binding: DialogAddContactBinding)
    }

}