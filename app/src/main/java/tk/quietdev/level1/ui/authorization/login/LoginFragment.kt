package tk.quietdev.level1.ui.authorization.login

import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
import tk.quietdev.level1.databinding.FragmentLoginBinding
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.ui.authorization.AuthActivity
import tk.quietdev.level1.ui.authorization.AuthViewModel
import tk.quietdev.level1.utils.DataState

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private val authSharedViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentLoginBinding.inflate(inflater, container, false).apply {
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       // showHelpTip()
        /*if (authSharedViewModel.isRemember.value!!) {
            authSharedViewModel.currentUserModel.value?.let {
                (activity as AuthActivity).login(it)
            }
        }*/
        setListeners()
        setObservers()

    }

    private fun setObservers() {
        viewModel.dataState.observe(viewLifecycleOwner) {
            Log.d("SSS", "setObservers: ${it}")
            when (it) {
                is DataState.Error -> {
                    val error = it.exception.message
                    error?.let { message ->
                        showErrorSnackbar(message)
                    }
                    showHelpTip()
                }
                is DataState.Loading -> {
                    Log.d("SSS", "LOAD: ")
                }
                is DataState.Success<UserModel> -> {
                    (activity as AuthActivity).login(it.data)
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
                authSharedViewModel.updateIsRemember(isChecked)
            }

            tvLinkSignUp.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegistrationFragment())
            }
        }
    }


    /**
     * checks if user is present in a database and proceeds to login if so
     */
    private fun tryLogin() {
        authSharedViewModel.currentUserModel.value?.let {
            (activity as AuthActivity).login(it)
        } ?: showHelpTip()
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