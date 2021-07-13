package tk.quietdev.assignment1


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import tk.quietdev.assignment1.databinding.ActivityAuthBinding

const val IS_REMEMBER = "isSaveChecked"
const val EMAIL = "email"
const val PASSWORD = "password"


private const val TAG = "AuthActivity"

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var db: DB
    private lateinit var preferences : SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        db = DB()
        binding = ActivityAuthBinding.inflate(layoutInflater)
        preferences = getPreferences(Context.MODE_PRIVATE)

        setContentView(binding.root)

        getCredentials()

        binding.btnRegister.setOnClickListener {
            login(it)
        }

    }



    private fun login(view: View) {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        val user = db.getUser(email, password)

        if (user != null) {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra(User.USERNAME, user.userName)
                putExtra(User.OCCUPATION, user.occupation)
                putExtra(User.PHYSICAL_ADDRESS, user.physicalAddress)
                putExtra(User.PICTURE, user.picture)
            }

            if (binding.checkBoxRemember.isChecked) {
                saveCredentials()
            } else {
                clearCredentials()
            }
            startActivity(intent)
            //finish() //should it be done like that?
        } else {
            Snackbar.make(view, "Try mail@pm.me : 11111 ", Snackbar.LENGTH_LONG)
                .setActionTextColor(Color.WHITE)
                .setAction("OK") {
                    binding.etEmail.setText("mail@pm.me")
                    binding.etPassword.setText("11111")
                }
                .show()
        }

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
        binding.etEmail.setText(preferences.getString(EMAIL,""))
        binding.etPassword.setText(preferences.getString(PASSWORD,""))
        binding.checkBoxRemember.isChecked = preferences.getBoolean(IS_REMEMBER,false)
    }


}