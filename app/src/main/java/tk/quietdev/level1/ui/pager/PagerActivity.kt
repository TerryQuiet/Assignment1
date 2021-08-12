package tk.quietdev.level1.ui.pager

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import tk.quietdev.level1.R
import tk.quietdev.level1.databinding.ActivityNavHostHolderBinding

class PagerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNavHostHolderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavHostHolderBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}