package tk.quietdev.level1.ui.authorization.register

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import tk.quietdev.level1.R
import tk.quietdev.level1.databinding.FragmentRegistrationBinding
import tk.quietdev.level1.data.remote.models.AuthResponse
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.ui.authorization.AuthActivity
import tk.quietdev.level1.ui.authorization.AuthViewModel
import tk.quietdev.level1.utils.Resource

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private val viewModel: RegisterViewModel by viewModels()
    private val authSharedViewModel: AuthViewModel by activityViewModels()

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
        viewModel.dataState.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                    val error = it.message
                    error?.let { message ->
                        showErrorSnackbar(message)
                    }
                    binding.progressCircular.visibility = View.GONE
                }
                is Resource.Loading -> {
                    binding.progressCircular.visibility = View.VISIBLE
                }
                is Resource.Success<UserModel?> -> {
                    (activity as AuthActivity).login()
                    binding.progressCircular.visibility = View.GONE
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
                authSharedViewModel.isRemember= isChecked
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