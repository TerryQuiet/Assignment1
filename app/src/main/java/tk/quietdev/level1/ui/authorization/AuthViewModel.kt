package tk.quietdev.level1.ui.authorization

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tk.quietdev.level1.database.FakeDatabase
import tk.quietdev.level1.models.User
import tk.quietdev.level1.utils.PrefsHelper
import tk.quietdev.level1.utils.Validator

class AuthViewModel(
    private val db: FakeDatabase,
    private val validator: Validator,
    private val prefs: PrefsHelper
) : ViewModel() {

    val isRemember = MutableLiveData(false)

    var currentUser: MutableLiveData<User> = MutableLiveData()

    fun loadPreferences() {
        prefs.apply {
            isRemember.value = getPreferences().getBoolean(IS_REMEMBER, false)
        }
        if (isRemember.value == true) {
            db.currentUserID = prefs.getIntOrNull(prefs.USER_ID)
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

    fun isPasswordValid(text: CharSequence?) =
        !text.isNullOrEmpty() && validator.isPasswordValid(text.toString())

    fun isEmailValid(text: CharSequence?) =
        !text.isNullOrEmpty() && validator.isEmailValid(text.toString())

    fun updateIsRemember(checked: Boolean) {
        prefs.saveBoolean(prefs.IS_REMEMBER, checked)
    }

    fun saveUser(user: User) {
        prefs.saveInt(prefs.USER_ID, user.id!!)
    }
}