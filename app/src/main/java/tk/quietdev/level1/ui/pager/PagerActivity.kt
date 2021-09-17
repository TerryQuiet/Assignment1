package tk.quietdev.level1.ui.pager

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import tk.quietdev.level1.R
import tk.quietdev.level1.databinding.ActivityPagerBinding

@AndroidEntryPoint
class PagerActivity : AppCompatActivity() {
    private var _binding: ActivityPagerBinding? = null
    private val binding get() = _binding!!
    private var navController: NavController? = null
    private val parentNavHost = R.id.ParentNavHost
    private val appbarSharedViewModel: AppbarSharedViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolbarSetup()
        setObservers()
        setListeners()
    }

    private fun setListeners() {
      binding.toolbar.setOnMenuItemClickListener {menuItem ->
          when (menuItem.itemId) {
              R.id.menu_search -> {
                  appbarSharedViewModel.showSearchLayout(true)
                  true
              }
              else -> false
          }
      }
    }

    private fun setObservers() {
        appbarSharedViewModel.currentNavController.observe(this, { navController ->
            navController?.let {
                binding.toolbar.setupWithNavController(it, AppBarConfiguration(it.graph))
            }
        })

        appbarSharedViewModel.navBarVisibility.observe(this) {
            binding.toolbar.visibility = it
        }

        appbarSharedViewModel.searchIconVisibility.observe(this) {
            val searchIcon = binding.toolbar.menu.findItem(R.id.menu_search)
            searchIcon.isVisible = it == View.VISIBLE
        }
    }

    private fun toolbarSetup() {
        val parentNavHost =
            supportFragmentManager.findFragmentById(parentNavHost) as? NavHostFragment
        navController = parentNavHost?.navController
        binding.toolbar.inflateMenu(R.menu.contacts_menu)
        val x = binding.toolbar.menu.findItem(R.id.menu_search)
        val appBarConfig = AppBarConfiguration(navController!!.graph)
        binding.toolbar.setupWithNavController(navController!!, appBarConfig)
        //binding.toolbar.visibility = View.GONE

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}