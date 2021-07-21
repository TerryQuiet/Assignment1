package tk.quietdev.level1.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import tk.quietdev.level1.database.MockDatabase
import tk.quietdev.level1.databinding.ActivitySettingsBinding
import tk.quietdev.level1.models.User
import tk.quietdev.level1.ui.contacts.ContactsActivity
import tk.quietdev.level1.utils.Const
import tk.quietdev.level1.utils.PrefsHelper
import tk.quietdev.level1.utils.ext.loadImage

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindListeners()
        bindValues()
    }

    private fun bindListeners() {
        binding.apply {
            btnViewContacts.setOnClickListener {
                openContacts()
            }
            btnEditProfile.setOnClickListener {
                // temporary
                PrefsHelper.clearPreferences()
            }
        }
    }

    private fun bindValues() {
        val currentUser = MockDatabase.getUserWithNoValidation(intent.getStringExtra(Const.EMAIL))
        binding.apply {
            tvName.text = currentUser?.userName
            tvAddress.text = currentUser?.physicalAddress
            tvOccupation.text = currentUser?.occupation
            ivProfilePic.loadImage(currentUser?.picture)
        }
    }

    private fun openContacts() {
        val intent = Intent(this, ContactsActivity::class.java)
        startActivity(intent)
        finish()
    }
}