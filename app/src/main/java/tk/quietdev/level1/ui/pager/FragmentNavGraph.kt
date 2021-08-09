package tk.quietdev.level1.ui.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import tk.quietdev.level1.databinding.ActivityContactsBinding

class FragmentNavGraph : Fragment() {
    private lateinit var binding: ActivityContactsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.actionBar?.title = "LOLOL"
        binding = ActivityContactsBinding.inflate(inflater, container, false)
        return binding.root
    }
}