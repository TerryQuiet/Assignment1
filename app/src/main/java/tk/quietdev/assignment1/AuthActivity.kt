package tk.quietdev.assignment1

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
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


}