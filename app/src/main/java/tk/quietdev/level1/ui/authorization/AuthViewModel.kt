package tk.quietdev.level1.ui.authorization

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import tk.quietdev.level1.database.FakeDatabase
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.utils.PrefsHelper
import tk.quietdev.level1.utils.Validator
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val db: FakeDatabase,
    private val validator: Validator,
    private val prefs: PrefsHelper
) : ViewModel() {

    val isRemember = MutableLiveData(false)

    var currentUserModel: MutableLiveData<UserModel> = MutableLiveData()

    fun loadPreferences() {
        prefs.apply {
            isRemember.value = getPreferences().getBoolean(IS_REMEMBER, false)
        }
        if (isRemember.value == true) {
            db.currentUserID = prefs.getIntOrNull(prefs.USER_ID)
            if (db.currentUserID != null) {
                currentUserModel.value = db.getUserWithNoValidation(db.currentUserID!!)
            } else {
                isRemember.value = false
            }
        }
    }

    fun findUser(email: String, password: String) {
        currentUserModel.value = db.getUserWithValidation(email, password)
    }

    fun isPasswordValid(text: CharSequence?) =
        !text.isNullOrEmpty() && validator.isPasswordValid(text.toString())

    fun isEmailValid(text: CharSequence?) =
        !text.isNullOrEmpty() && validator.isEmailValid(text.toString())

    fun updateIsRemember(checked: Boolean) {
        prefs.saveBoolean(prefs.IS_REMEMBER, checked)
    }

    fun saveUser(userModel: UserModel) {
        prefs.saveInt(prefs.USER_ID, userModel._id!!)
    }
}