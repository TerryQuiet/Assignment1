package tk.quietdev.level1.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import tk.quietdev.level1.database.Database
import tk.quietdev.level1.databinding.ActivitySettingsBinding
import tk.quietdev.level1.models.User
import tk.quietdev.level1.utils.Const
import tk.quietdev.level1.utils.PrefsHelper
import tk.quietdev.level1.utils.ext.loadImage

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentUser = Database.getUserWithNoValidation(intent.getStringExtra(Const.EMAIL))
        bindValues(currentUser)
    }

    private fun bindValues(currentUser : User?) {
        binding.apply {
            btnEditProfile.setOnClickListener {
                // temporary
                PrefsHelper.clearPreferences()
            }
            tvName.text = currentUser?.userName
            tvAddress.text = currentUser?.physicalAddress
            tvOccupation.text = currentUser?.occupation
            ivProfilePic.loadImage(currentUser?.picture)
        }
    }
}