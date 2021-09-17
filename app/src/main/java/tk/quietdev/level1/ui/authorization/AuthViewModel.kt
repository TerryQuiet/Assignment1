package tk.quietdev.level1.ui.authorization

import android.util.Log
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
    private val validator: Validator,
    private val prefs: PrefsHelper
) : ViewModel() {

    var isRemember = false

    init {
        prefs.apply {
            isRemember = getPreferences().getBoolean(IS_REMEMBER, false)
        }
    }

    fun isPasswordValid(text: CharSequence?) =
        !text.isNullOrEmpty() && validator.isPasswordValid(text.toString())

    fun isEmailValid(text: CharSequence?) =
        !text.isNullOrEmpty() && validator.isEmailValid(text.toString())

    fun updateIsRemember() {
        prefs.saveBoolean(prefs.IS_REMEMBER, isRemember)
    }

}