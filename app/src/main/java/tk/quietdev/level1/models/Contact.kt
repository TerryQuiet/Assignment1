package tk.quietdev.level1.models

/**
 * to get contacts from phone
 */
data class Contact(val id: String, val name: String) {
    var photo: String? = null
    var numbers = ArrayList<String>()
    var emails = ArrayList<String>()
}