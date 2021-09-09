package tk.quietdev.level1.ui.authorization.register

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import tk.quietdev.level1.R
import tk.quietdev.level1.databinding.FragmentRegistrationBinding
import tk.quietdev.level1.data.remote.models.AuthResonse
import tk.quietdev.level1.ui.authorization.AuthViewModel

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private val viewModel: RegisterViewModel by viewModels()
    private val authSharedViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentRegistrationBinding.inflate(inflater, container, false).apply {
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setObservers()
    }

    private fun setObservers() {
        viewModel.regResponse.observe(viewLifecycleOwner) {
            when (it) {
                AuthResonse.Status.ONGOING -> {
                    // show animation
                }
                AuthResonse.Status.OK -> {
                    findNavController().navigate(RegisterFragmentDirections.regToLog())
                }
                AuthResonse.Status.BAD -> {
                    showErrorSnackbar(viewModel.errorMessage)
                }
                AuthResonse.Status.NULL -> {
                    // request is not send yet
                }
            }
        }
    }

    private fun setListeners() {
        binding.apply {
            btnRegister.setOnClickListener {
                viewModel.regUser(
                    email = etEmail.text.toString(),
                    passwd = etPassword.text.toString()
                )
            }
            etEmail.apply {
                doOnTextChanged { text, _, _, _ ->
                    if (authSharedViewModel.isEmailValid(text)) {
                        binding.etEmailParent.isErrorEnabled = false
                    } else {
                        binding.etEmailParent.error = getString(R.string.please_enter_valid_email)
                    }
                }
                /* Lower error message disappears when not focused, but frame stays RED if error */
                setOnFocusChangeListener { _, hasFocus ->
                    if (hasFocus && etEmailParent.isErrorEnabled) {
                        etEmailParent.error = getString(R.string.please_enter_valid_email)
                    } else if (etEmailParent.isErrorEnabled) {
                        etEmailParent.error = " "
                    }
                }
            }
            etPassword.doOnTextChanged { text, _, _, _ ->
                if (authSharedViewModel.isPasswordValid(text)) {
                    binding.etPasswordParent.isErrorEnabled = false
                } else {
                    binding.etPasswordParent.error = getString(R.string.please_enter_valid_password)
                }
            }
            cbRemember.setOnCheckedChangeListener { _, isChecked ->
                authSharedViewModel.updateIsRemember(isChecked)
            }
            tvLinkSignIn.setOnClickListener {
                findNavController().navigate(RegisterFragmentDirections.regToLog())
            }
        }
    }

    private fun showErrorSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setTextColor(Color.WHITE)
            .show()
    }
}