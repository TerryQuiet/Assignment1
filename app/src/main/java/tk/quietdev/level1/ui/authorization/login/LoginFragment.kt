package tk.quietdev.level1.ui.authorization.login

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
import tk.quietdev.level1.databinding.FragmentLoginBinding
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.ui.authorization.AuthActivity
import tk.quietdev.level1.ui.authorization.AuthViewModel
import tk.quietdev.level1.utils.Resource

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private val authSharedViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentLoginBinding.inflate(inflater, container, false).apply {
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
                    showHelpTip()
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
                viewModel.loginUser(
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
                authSharedViewModel.isRemember = isChecked
                Log.d("TAG", "setListeners: ${authSharedViewModel.isRemember}")
            }

            tvLinkSignUp.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegistrationFragment())
            }
        }
    }

    @Deprecated("Not required, but helps with login(fills the fields for you if pressed OK)")
    private fun showHelpTip() {
        Snackbar.make(
            binding.btnRegister, getString(
                R.string.login_help_tip
            ), Snackbar.LENGTH_LONG
        )
            .setAction("OK") {
                binding.etEmail.setText("mail@pm.me")
                binding.etPassword.setText("11111")
            }
            .setTextColor(Color.WHITE)
            .show()
    }

    private fun showErrorSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setTextColor(Color.WHITE)
            .show()
    }
}