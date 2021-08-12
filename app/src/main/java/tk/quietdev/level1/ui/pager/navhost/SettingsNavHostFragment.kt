package tk.quietdev.level1.ui.pager.navhost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import tk.quietdev.level1.R
import tk.quietdev.level1.databinding.FragmentNavhostSettingsBinding
import tk.quietdev.level1.ui.pager.AppbarSharedViewModel

class SettingsNavHostFragment : Fragment() {

    var binding: FragmentNavhostSettingsBinding? = null
    private val appbarSharedViewModel: AppbarSharedViewModel by sharedViewModel()
    private var navController: NavController? = null
    private val nestedNavHostFragmentId = R.id.nestedSettingsNavHostFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentNavhostSettingsBinding.inflate(inflater, container, false).apply {
            binding = this
            //binding.lifecycleOwner = viewLifecycleOwner
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val nestedNavHostFragment =
            childFragmentManager.findFragmentById(nestedNavHostFragmentId) as? NavHostFragment
        navController = nestedNavHostFragment?.navController

    }



    override fun onResume() {
        super.onResume()
//        callback.isEnabled = true
        // Set this navController as ViewModel's navController
        appbarSharedViewModel.currentNavController.value = navController
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}