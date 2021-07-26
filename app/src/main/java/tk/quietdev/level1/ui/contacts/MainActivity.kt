package tk.quietdev.level1.ui.contacts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tk.quietdev.level1.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}