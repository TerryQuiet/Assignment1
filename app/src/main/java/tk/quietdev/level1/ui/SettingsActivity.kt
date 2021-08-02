package tk.quietdev.level1.ui


import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    /*private lateinit var binding: ActivitySettingsBinding
    private lateinit var userDetailBinding: UserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        userDetailBinding = binding.topContainer
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
               // PrefsHelper.clearPreferences()
            }
        }
    }

    private fun bindValues() {
        val currentUser = FakeDatabase.getUserWithNoValidation(intent.getStringExtra(Const.EMAIL))
        binding.topContainer.apply {
            tvName.text = currentUser?.userName
            tvAddress.text = currentUser?.physicalAddress
            tvOccupation.text = currentUser?.occupation
            ivProfilePic.loadImage(currentUser?.picture)
        }
    }

    private fun openContacts() {
       *//* val intent = Intent(this, ContactsActivity::class.java)
        startActivity(intent)
        finish()*//*
    }*/
}