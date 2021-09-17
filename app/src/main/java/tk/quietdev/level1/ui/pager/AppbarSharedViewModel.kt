package tk.quietdev.level1.ui.pager

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppbarSharedViewModel @Inject constructor() : ViewModel() {
    val currentNavController = MutableLiveData<NavController?>()

    val navBarVisibility = MutableLiveData(View.VISIBLE)
    val searchLayoutVisibility = MutableLiveData(View.GONE)
    val searchIconVisibility = MutableLiveData(View.GONE)
    val searchText = MutableLiveData("")

    fun showSearchLayout(toShow : Boolean) {
        if (!toShow) {
            searchLayoutVisibility.value = View.GONE
            navBarVisibility.value = View.VISIBLE
        } else {
            searchLayoutVisibility.value = View.VISIBLE
            navBarVisibility.value = View.GONE
        }
    }

}