package tk.quietdev.level1.ui.pager.navhost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import tk.quietdev.level1.R
import tk.quietdev.level1.databinding.FragmentNavhostSettingsBinding
import tk.quietdev.level1.ui.pager.AppbarSharedViewModel

@AndroidEntryPoint
class SettingsNavHostFragment : Fragment() {

    var binding: FragmentNavhostSettingsBinding? = null

    private val appbarSharedViewModel: AppbarSharedViewModel by activityViewModels()
    private var navController: NavController? = null
    private val nestedNavHostFragmentId = R.id.nestedSettingsNavHostFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentNavhostSettingsBinding.inflate(inflater, container, false).apply {
            binding = this
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nestedNavHostFragment =
            childFragmentManager.findFragmentById(nestedNavHostFragmentId) as? NavHostFragment
        navController = nestedNavHostFragment?.navController

    }


    override fun onResume() {
        super.onResume()
        appbarSharedViewModel.currentNavController.value = navController
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}