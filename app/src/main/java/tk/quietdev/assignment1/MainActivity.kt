package tk.quietdev.assignment1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import tk.quietdev.assignment1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setTitle(R.string.settings)
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