package tk.quietdev.level1.ui.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import tk.quietdev.level1.R
import tk.quietdev.level1.databinding.FragmentNavGraphBinding

class FragmentNavGraph : Fragment() {
    private lateinit var binding: FragmentNavGraphBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNavGraphBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager.findFragmentById(R.id.nav_host)?.findNavController()?.let {
            (activity as PagerActivity).setupAppBar(it)
        }

    }


    /* */
}