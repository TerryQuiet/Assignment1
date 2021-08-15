package tk.quietdev.level1.models.convertors

import tk.quietdev.level1.models.ContactModel
import tk.quietdev.level1.models.UserModel

class Convertor {

    /**
     * @return only contacts with image, and phone number
     */
    fun convertContactToUser(contactModel: ContactModel): UserModel? {
        val number: String? = if (contactModel.numbers.isEmpty()) null else contactModel.numbers.first()
        val photo = contactModel.photo
        if (number.isNullOrEmpty() || photo.isNullOrEmpty())
            return null

        val email =
            if (contactModel.emails.isEmpty()) "${contactModel.name}@fake.mail" else contactModel.emails.first()

        return UserModel(
            id = null,
            userName = contactModel.name,
            phone = number,
            email = email,
            pictureUri = photo
        )
    }
}