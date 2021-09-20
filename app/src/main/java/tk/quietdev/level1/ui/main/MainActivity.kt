package tk.quietdev.level1.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import tk.quietdev.level1.R
import tk.quietdev.level1.databinding.ActivityPagerBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityPagerBinding? = null
    private val binding get() = _binding!!
    private val appbarSharedViewModel: AppbarSharedViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolbarSetup()
        setObservers()
        setListeners()

    }

    private fun setListeners() {
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_search -> {
                    appbarSharedViewModel.showSearchLayout(true)
                    true
                }
                else -> false
            }
        }

        binding.ivCross.setOnClickListener {
            appbarSharedViewModel.showSearchLayout(false)
        }

        binding.etSearch.doOnTextChanged { text, _, _, _ ->
            appbarSharedViewModel.searchText.value = text.toString()
        }
    }

    private fun setObservers() {

        appbarSharedViewModel.searchLayoutVisibility.observe(this) {
            binding.searchTopBar.visibility = it
        }

        appbarSharedViewModel.navBarVisibility.observe(this) {
            binding.toolbar.visibility = it
        }

        appbarSharedViewModel.searchIconVisibility.observe(this) {
            val searchIcon = binding.toolbar.menu.findItem(R.id.menu_search)
            searchIcon?.isVisible = it == View.VISIBLE
        }
    }

    private fun toolbarSetup() {
        val parentNavHost =
            supportFragmentManager.findFragmentById(R.id.ParentNavHost) as NavHostFragment
        navController = parentNavHost.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)

        binding.toolbar.inflateMenu(R.menu.contacts_menu)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }


}