package tk.quietdev.level1.ui.contacts.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import tk.quietdev.level1.databinding.DialogAddContactBinding


class AddContactDialog(
) : DialogFragment() {

    private lateinit var binding: DialogAddContactBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bntCancel.setOnClickListener {
            dismiss()
        }

        binding.btnAdd.setOnClickListener {
           // onClick.onDialogAddClicked(binding)
            dismiss()
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

    interface Listener {
        fun onDialogAddClicked(binding: DialogAddContactBinding)
    }

}