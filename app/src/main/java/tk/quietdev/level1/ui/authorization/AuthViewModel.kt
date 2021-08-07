package tk.quietdev.level1.ui.authorization

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tk.quietdev.level1.database.FakeDatabase
import tk.quietdev.level1.models.User
import tk.quietdev.level1.utils.PrefsHelper

class AuthViewModel(private val db: FakeDatabase):ViewModel() {

    val isRemember = MutableLiveData(false)

    var currentUser: MutableLiveData<User> = MutableLiveData()

    fun loadPreferences() {
        PrefsHelper.apply {
            isRemember.value = getPreferences().getBoolean(IS_REMEMBER, false)
        }
        if (isRemember.value == true) {
            db.currentUserID = PrefsHelper.getIntOrNull(PrefsHelper.USER_ID)
            if (db.currentUserID != null) {
                currentUser.value = db.getUserWithNoValidation(db.currentUserID!!)
            } else {
                isRemember.value = false
            }
        }
    }

    fun findUser(email: String, password: String) {
        currentUser.value = db.getUserWithValidation(email, password)
    }
}