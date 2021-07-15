package tk.quietdev.level1.data

import tk.quietdev.level1.R

data class User(
    var userName: String? = null,
    var email: String,
    var occupation: String = "Not provided",
    var physicalAddress: String = "Not provided",
    var picture: Int = R.drawable.ic_profile,
    private var password: String
) {
    fun isPasswordCorrect(inputPassword: String): Boolean {
        return password == inputPassword
    }

    companion object {
        const val USERNAME = "userName"
        const val OCCUPATION = "occupation"
        const val PHYSICAL_ADDRESS = "physicalAddress"
        const val PICTURE = "picture"
    }
}


