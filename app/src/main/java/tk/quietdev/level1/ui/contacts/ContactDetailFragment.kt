package tk.quietdev.level1.ui.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import tk.quietdev.level1.databinding.FragmentContactDetailBinding


class ContactDetailFragment : Fragment() {
    private lateinit var binding: FragmentContactDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentContactDetailBinding.inflate(inflater, container, false).apply { binding = this }.root

}