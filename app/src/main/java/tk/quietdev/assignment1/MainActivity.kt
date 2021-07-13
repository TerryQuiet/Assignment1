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
        binding.tvName.text = intent.getStringExtra(User.USERNAME)
        binding.tvAddress.text = intent.getStringExtra(User.PHYSICAL_ADDRESS)
        binding.tvOccupation.text = intent.getStringExtra(User.OCCUPATION)
    }
}