package tk.quietdev.level1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import tk.quietdev.level1.data.User
import tk.quietdev.level1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.settings)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindValues()

    }

    private fun bindValues() {
        binding.apply {
            tvName.text = intent.getStringExtra(User.USERNAME)
            tvAddress.text = intent.getStringExtra(User.PHYSICAL_ADDRESS)
            tvOccupation.text = intent.getStringExtra(User.OCCUPATION)
            ivProfilePic.setImageResource(intent.getIntExtra(User.PICTURE, R.drawable.ic_profile))
        }
    }
}