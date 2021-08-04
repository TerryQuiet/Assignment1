package tk.quietdev.level1.models

data class Contact(val id: String, val name:String) {
    var photo: String? = null
    var numbers = ArrayList<String>()
    var emails = ArrayList<String>()
}