package tk.quietdev.level1.utils

import java.util.regex.Pattern


private val EMAIL_ADDRESS_PATTERN = Pattern.compile(
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
)

object Validator {
    fun isEmailValid(mail: String): Boolean{
        return EMAIL_ADDRESS_PATTERN.matcher(mail).matches()
    }

    fun isPasswordValid(toString: String): Boolean {
        return toString.length >= 5
    }
}