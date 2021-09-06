package tk.quietdev.level1.ui.authorization

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import tk.quietdev.level1.databinding.ActivityAuthBinding
import tk.quietdev.level1.models.UserModel

import tk.quietdev.level1.ui.pager.PagerActivity
import tk.quietdev.level1.utils.Const

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        authViewModel.loadPreferences()
    }

    fun login(userModel: UserModel) {
        val intent = Intent(this, PagerActivity::class.java).apply {
            putExtra(Const.USER, userModel)
        }
        authViewModel.saveUser(userModel)
        startActivity(intent)
        finish()
    }

}