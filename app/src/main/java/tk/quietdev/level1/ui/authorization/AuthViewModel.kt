package tk.quietdev.level1.ui.authorization

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.repository.Repository
import tk.quietdev.level1.utils.PrefsHelper
import tk.quietdev.level1.utils.Validator
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val db: Repository,
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
            val userId = prefs.getIntOrNull(prefs.USER_ID)
            userId?.let {
                currentUserModel.value = db.getUserWithNoValidation(it)
            } ?: run {
                isRemember.value = false
            }
        }
    }


    fun isPasswordValid(text: CharSequence?) =
        !text.isNullOrEmpty() && validator.isPasswordValid(text.toString())

    fun isEmailValid(text: CharSequence?) =
        !text.isNullOrEmpty() && validator.isEmailValid(text.toString())

    fun updateIsRemember(checked: Boolean) {
        prefs.saveBoolean(prefs.IS_REMEMBER, checked)
    }

    fun saveUser(userModel: UserModel) {
        prefs.saveInt(prefs.USER_ID, userModel.id!!)
    }

}