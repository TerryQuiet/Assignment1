package tk.quietdev.level1.ui.authorization

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import tk.quietdev.level1.R
import tk.quietdev.level1.databinding.ActivityAuthBinding
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.ui.pager.PagerActivity
import tk.quietdev.level1.utils.Const

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private val viewModel: AuthViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.loadPreferences()

        showHelpTip()

        if (viewModel.isRemember.value!!) {
            viewModel.currentUserModel.value?.let {
                login(it)
            }
        }
        setListeners()
    }


    private fun setListeners() {
        binding.apply {
            btnRegister.setOnClickListener {
                tryLogin()
            }
            etEmail.apply {
                doOnTextChanged { text, _, _, _ ->
                    if (viewModel.isEmailValid(text)) {
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
                if (viewModel.isPasswordValid(text)) {
                    binding.etPasswordParent.isErrorEnabled = false
                } else {
                    binding.etPasswordParent.error = getString(R.string.please_enter_valid_password)
                }
            }
            cbRemember.setOnCheckedChangeListener { _, isChecked ->
                viewModel.updateIsRemember(isChecked)
            }
        }
    }


    /**
     * checks if user is present in a database and proceeds to login if so
     */


    private fun tryLogin() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        viewModel.findUser(email, password)
        val user = viewModel.currentUserModel.value
        if (user != null) {
            login(user)
        } else {
            showHelpTip()
        }

    }

    @Deprecated("Not required, but helps with login(fills the fields for you if pressed OK)")
    private fun showHelpTip() {
        Snackbar.make(binding.btnRegister, getString(R.string.login_help_tip), Snackbar.LENGTH_LONG)
            .setAction("OK") {
                binding.etEmail.setText("mail@pm.me")
                binding.etPassword.setText("11111")
            }
            .setTextColor(Color.WHITE)
            .show()
    }

    private fun login(userModel: UserModel) {
        val intent = Intent(this, PagerActivity::class.java).apply {
            putExtra(Const.USER, userModel)
        }
        viewModel.saveUser(userModel)
        startActivity(intent)
        finish()
    }


}