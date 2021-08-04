package tk.quietdev.level1.models.convertors

import tk.quietdev.level1.models.Contact
import tk.quietdev.level1.models.User

class Convertor {

    /**
     * @return only contacts with image, and phone number
     */
    fun convertContactToUser(contact: Contact): User? {
        val number: String? = if (contact.numbers.isEmpty()) null else contact.numbers.first()
        val photo = contact.photo
        if (number.isNullOrEmpty() || photo.isNullOrEmpty())
            return null

        val email =
            if (contact.emails.isEmpty()) "${contact.name}@fake.mail" else contact.emails.first()

        return User(
            id = null,
            userName = contact.name,
            phone = number,
            email = email,
            pictureUri = photo
        )
    }
}