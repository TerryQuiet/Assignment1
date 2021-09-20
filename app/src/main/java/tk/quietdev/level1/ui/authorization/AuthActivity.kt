package tk.quietdev.level1.ui.authorization

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import tk.quietdev.level1.databinding.ActivityAuthBinding
import tk.quietdev.level1.ui.main.MainActivity

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (authViewModel.isRemember)
            login()
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    fun login() {
        authViewModel.updateIsRemember()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}