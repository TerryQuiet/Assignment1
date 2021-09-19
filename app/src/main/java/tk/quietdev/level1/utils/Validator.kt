package tk.quietdev.level1.utils

import android.util.Patterns
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Validator @Inject constructor() {
    fun isEmailValid(mail: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(mail).matches()
    }

    fun isPasswordValid(toString: String): Boolean {
        return toString.length >= 5
    }
}