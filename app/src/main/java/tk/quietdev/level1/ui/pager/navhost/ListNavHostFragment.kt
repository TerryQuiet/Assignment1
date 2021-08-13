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
import tk.quietdev.level1.databinding.FragmentNavhostListBinding
import tk.quietdev.level1.ui.pager.AppbarSharedViewModel

class ListNavHostFragment : Fragment() {

    private var _binding: FragmentNavhostListBinding? = null
    private val appbarSharedViewModel: AppbarSharedViewModel by sharedViewModel()
    private var navController: NavController? = null
    private val nestedNavHostFragmentId = R.id.nestedListNavHostFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentNavhostListBinding.inflate(inflater, container, false).apply {
            _binding = this
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nestedNavHostFragment =
            childFragmentManager.findFragmentById(nestedNavHostFragmentId) as? NavHostFragment
        navController = nestedNavHostFragment?.navController
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    override fun onResume() {
        super.onResume()
        // Set this navController as ViewModel's navController
        appbarSharedViewModel.currentNavController.value = navController
    }
}