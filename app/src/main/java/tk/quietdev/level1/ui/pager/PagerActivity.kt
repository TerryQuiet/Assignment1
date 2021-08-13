package tk.quietdev.level1.ui.pager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import tk.quietdev.level1.R
import tk.quietdev.level1.databinding.ActivityPagerBinding


class PagerActivity : AppCompatActivity() {
    private var _binding: ActivityPagerBinding? = null
    private val binding get() = _binding!!
    private var navController: NavController? = null
    private val parentNavHost = R.id.ParentNavHost
    private val appbarSharedViewModel: AppbarSharedViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolbarSetup()

    }

    private fun toolbarSetup() {
        val parentNavHost =
            supportFragmentManager.findFragmentById(parentNavHost) as? NavHostFragment
        navController = parentNavHost?.navController

        val appBarConfig = AppBarConfiguration(navController!!.graph)
        binding.toolbar.setupWithNavController(navController!!, appBarConfig)

        appbarSharedViewModel.currentNavController.observe(this, { navController ->
            navController?.let {
                binding.toolbar.setupWithNavController(it, AppBarConfiguration(it.graph))
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}