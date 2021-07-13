package tk.quietdev.assignment1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import tk.quietdev.assignment1.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var db: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        db = DB()
        binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnRegister.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        val user = db.getUser(email, password)

        if (user != null) {
            val intent = Intent(this, MainActivity::class.java).apply {
               // putExtra(EXTRA_MESSAGE, user.userName)
            }
            startActivity(intent)
        }


    }


}