package tk.quietdev.level1


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.google.android.material.snackbar.Snackbar
import tk.quietdev.level1.data.DB
import tk.quietdev.level1.data.User
import tk.quietdev.level1.databinding.ActivityAuthBinding
import tk.quietdev.level1.utils.Validator

private const val IS_REMEMBER = "isSaveChecked"
private const val IS_AUTOLOGIN = "isAutoLoginChecked"
private const val EMAIL = "email"
private const val PASSWORD = "password"

private const val TAG = "AuthActivity"

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var db: DB
    private lateinit var preferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        db = DB()
        binding = ActivityAuthBinding.inflate(layoutInflater)
        preferences = getPreferences(Context.MODE_PRIVATE)
        setContentView(binding.root)

        getCredentials()
        showHelpToast()

        if (binding.checkBoxAutologin.isChecked) {
            tryLogin()
        }

        binding.apply {
            btnRegister.setOnClickListener {
                tryLogin()
            }
            checkBoxAutologin.setOnClickListener {
                preferences.edit()
                    .putBoolean(IS_AUTOLOGIN, binding.checkBoxAutologin.isChecked)
                    .apply()
            }
            etEmail.doOnTextChanged { text, _, _, _ ->
                if (!text.isNullOrEmpty() && Validator.isEmailValid(text.toString())) {
                    binding.etEmailParent.isErrorEnabled = false
                } else {
                    binding.etEmailParent.error = resources.getString(R.string.please_enter_valid_email)
                }
            }
            etPassword.doOnTextChanged { text, _, _, _ ->
                if (!text.isNullOrEmpty() && Validator.isPasswordValid(text.toString())) {
                    binding.etPasswordParent.isErrorEnabled = false
                } else {
                    binding.etPasswordParent.error = resources.getString(R.string.please_enter_valid_password)
                }
            }
        }


    }


    /**
     * checks if user is present in a database and proceeds to login if so
     */

    private fun tryLogin() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val user = db.getUser(email, password)

        if (user != null) {
            if (binding.checkBoxRemember.isChecked) {
                saveCredentials()
            } else {
                clearCredentials()
            }
            login(user)
        } else {
            showHelpToast()
        }

    }


    private fun showHelpToast() {
        Snackbar.make(binding.root, "Try mail@pm.me : 11111 ", Snackbar.LENGTH_LONG)
            .setAction("OK") {
                binding.etEmail.setText("mail@pm.me")
                binding.etPassword.setText("11111")
            }
            .setTextColor(Color.WHITE) // I tried to change it in theme.xml, but it doesn't work... help required
            .show()
    }

    private fun login(user: User) {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra(User.USERNAME, user.userName)
            putExtra(User.OCCUPATION, user.occupation)
            putExtra(User.PHYSICAL_ADDRESS, user.physicalAddress)
            putExtra(User.PICTURE, user.picture)
        }
        startActivity(intent)
        //finish() //should it be done like that?
    }

    private fun clearCredentials() {
        preferences.edit().clear().apply()
    }

    private fun saveCredentials() {
        preferences.edit()
            .putBoolean(IS_REMEMBER, binding.checkBoxRemember.isChecked)
            .putString(EMAIL, binding.etEmail.text.toString())
            .putString(PASSWORD, binding.etPassword.text.toString())
            .apply()
        Log.d(TAG, "saveCredentials: SAVED")
    }

    private fun getCredentials() {
        binding.etEmail.setText(preferences.getString(EMAIL, ""))
        binding.etPassword.setText(preferences.getString(PASSWORD, ""))
        binding.checkBoxRemember.isChecked = preferences.getBoolean(IS_REMEMBER, false)
        binding.checkBoxAutologin.isChecked = preferences.getBoolean(IS_AUTOLOGIN, false)
    }


}