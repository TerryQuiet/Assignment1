package tk.quietdev.level1.ui.pager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import tk.quietdev.level1.R
import tk.quietdev.level1.databinding.ActivityPagerBinding


class PagerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPagerBinding
    private var navController: NavController? = null
    private val nestedNavHostFragmentId = R.id.nestedParentNavHostFragment
    private val appbarSharedViewModel: AppbarSharedViewModel by viewModel() // TODO: 8/13/2021 by inject? 

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val nestedNavHostFragment =
            supportFragmentManager.findFragmentById(nestedNavHostFragmentId) as? NavHostFragment
        navController = nestedNavHostFragment?.navController

        val appBarConfig = AppBarConfiguration(navController!!.graph)
        binding!!.toolbar.setupWithNavController(navController!!, appBarConfig)


        appbarSharedViewModel.currentNavController.observe(this, Observer { navController ->
            navController?.let {
                val appBarConfig = AppBarConfiguration(it.graph)
                binding!!.toolbar.setupWithNavController(it, appBarConfig)
            }
        })
    }

}