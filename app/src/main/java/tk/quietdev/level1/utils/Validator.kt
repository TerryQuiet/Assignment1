package tk.quietdev.level1.utils

import android.util.Patterns

class Validator {
    fun isEmailValid(mail: String): Boolean{
       return  Patterns.EMAIL_ADDRESS.matcher(mail).matches()
    }

    fun isPasswordValid(toString: String): Boolean {
        return toString.length >= 5
    }
}