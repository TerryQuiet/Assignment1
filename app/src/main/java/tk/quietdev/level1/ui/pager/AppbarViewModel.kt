package tk.quietdev.level1.ui.pager

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class AppbarViewModel : ViewModel() {
    val currentNavController = MutableLiveData<NavController?>()

}