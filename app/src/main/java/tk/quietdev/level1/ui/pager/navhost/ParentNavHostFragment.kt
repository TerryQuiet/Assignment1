package tk.quietdev.level1.ui.pager.navhost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import tk.quietdev.level1.R
import tk.quietdev.level1.databinding.FragmentNavhostParentBinding
import tk.quietdev.level1.ui.pager.AppbarSharedViewModel


class ParentNavHostFragment : Fragment() {

    var binding: FragmentNavhostParentBinding? = null
    private var navController: NavController? = null
    private val nestedNavHostFragmentId = R.id.nestedParentNavHostFragment
    private val appbarSharedViewModel: AppbarSharedViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentNavhostParentBinding.inflate(inflater, container, false).apply {
            binding = this
           // binding.lifecycleOwner = viewLifecycleOwner
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val nestedNavHostFragment =
            childFragmentManager.findFragmentById(nestedNavHostFragmentId) as? NavHostFragment
        navController = nestedNavHostFragment?.navController

        val appBarConfig = AppBarConfiguration(navController!!.graph)
        binding!!.toolbar.setupWithNavController(navController!!, appBarConfig)


        appbarSharedViewModel.currentNavController.observe(viewLifecycleOwner, Observer { navController ->
            navController?.let {
                val appBarConfig = AppBarConfiguration(it.graph)
                binding!!.toolbar.setupWithNavController(it, appBarConfig)
            }
        })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}