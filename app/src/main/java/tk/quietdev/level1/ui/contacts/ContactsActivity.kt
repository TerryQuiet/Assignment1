package tk.quietdev.level1.ui.contacts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tk.quietdev.level1.databinding.ActivityContactsBinding



class ContactsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}