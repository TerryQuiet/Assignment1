package tk.quietdev.level1.ui.pager

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppbarSharedViewModel @Inject constructor() : ViewModel() {
    val currentNavController = MutableLiveData<NavController?>()

}