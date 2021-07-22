package tk.quietdev.level1.ui


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.google.android.material.snackbar.Snackbar
import tk.quietdev.level1.R
import tk.quietdev.level1.database.FakeDatabase
import tk.quietdev.level1.databinding.ActivityAuthBinding
import tk.quietdev.level1.utils.Const
import tk.quietdev.level1.utils.PrefsHelper
import tk.quietdev.level1.utils.Validator


class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private val db = FakeDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadPreferences()

        showHelpTip()

        if (binding.cbRemember.isChecked) {
            if (db.currentUserID.isNotEmpty()) {
                login(db.currentUserID)
            }
        }
        setListeners()
    }

    private fun loadPreferences() {
        binding.cbRemember.isChecked = PrefsHelper.getRememberState()
    }

    private fun setListeners() {
        binding.apply {
            btnRegister.setOnClickListener {
                tryLogin()
            }
            etEmail.apply {
                doOnTextChanged { text, _, _, _ ->
                    if (!text.isNullOrEmpty() && Validator.isEmailValid(text.toString())) {
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
                if (!text.isNullOrEmpty() && Validator.isPasswordValid(text.toString())) {
                    binding.etPasswordParent.isErrorEnabled = false
                } else {
                    binding.etPasswordParent.error = getString(R.string.please_enter_valid_password)
                }
            }
            cbRemember.setOnCheckedChangeListener { _, isChecked ->
                PrefsHelper.saveRememberState(isChecked)
            }
        }
    }

    /**
     * checks if user is present in a database and proceeds to login if so
     */

    private fun tryLogin() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val user = db.getUserWithValidation(email, password)
        if (user != null) {
            db.currentUserID = email
            login(email)
        } else {
            showHelpTip()
        }

    }

    @Deprecated("Not required, but helps with login(fills if pressed OK")
    private fun showHelpTip() {
        Snackbar.make(binding.btnRegister, getString(R.string.login_help_tip), Snackbar.LENGTH_LONG)
            .setAction("OK") {
                binding.etEmail.setText("mail@pm.me")
                binding.etPassword.setText("11111")
            }
            .setTextColor(Color.WHITE)
            .show()
    }

    private fun login(email: String) {
        // it's required by a task to send something in intend.
        val intent = Intent(this, SettingsActivity::class.java).apply {
            putExtra(Const.EMAIL, email)
        }
        PrefsHelper.saveCurrentUserID(email)
        startActivity(intent)
        finish()
    }




}