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
import org.koin.androidx.viewmodel.ext.android.viewModel
import tk.quietdev.level1.R
import tk.quietdev.level1.databinding.FragmentNavhostListBinding
import tk.quietdev.level1.ui.pager.AppbarViewModel

class ListNavHostFragment : Fragment() {

    lateinit var binding: FragmentNavhostListBinding
    private val appbarViewModel: AppbarViewModel by sharedViewModel()
    private var navController: NavController? = null
    private val nestedNavHostFragmentId = R.id.nestedListNavHostFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentNavhostListBinding.inflate(inflater, container, false).apply {
            binding = this
            binding.lifecycleOwner = viewLifecycleOwner
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val nestedNavHostFragment =
            childFragmentManager.findFragmentById(nestedNavHostFragmentId) as? NavHostFragment
        navController = nestedNavHostFragment?.navController

        // Listen on back press
//        listenOnBackPressed()

    }

//    private fun listenOnBackPressed() {
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
//    }


    override fun onResume() {
        super.onResume()
//        callback.isEnabled = true

        // Set this navController as ViewModel's navController
        appbarViewModel.currentNavController.value = navController
    }

    override fun onPause() {
        super.onPause()
//        callback.isEnabled = false
    }

    /**
     * This callback should be created with Disabled because on rotation ViewPager creates
     * NavHost fragments that are not on screen, destroys them afterwards but it might take
     * up to 5 seconds.
     *
     * ### Note: During that interval touching back button sometimes call incorrect [OnBackPressedCallback.handleOnBackPressed] instead of this one if callback is **ENABLED**
     */
//    val callback = object : OnBackPressedCallback(false) {
//
//        override fun handleOnBackPressed() {
//
//            // Check if it's the root of nested fragments in this navhost
//            if (navController?.currentDestination?.id == navController?.graph?.startDestination) {
//
//                Toast.makeText(requireContext(), "🏠 AT START DESTINATION ", Toast.LENGTH_SHORT)
//                    .show()
//
//                /*
//                    Disable this callback because calls OnBackPressedDispatcher
//                     gets invoked  calls this callback  gets stuck in a loop
//                 */
//                isEnabled = false
//                requireActivity().onBackPressed()
//                isEnabled = true
//
//            } else {
//                navController?.navigateUp()
//            }
//
//        }
//    }

}