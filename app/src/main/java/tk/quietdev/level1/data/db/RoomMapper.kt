package tk.quietdev.level1.data.db

import tk.quietdev.level1.data.db.model.CurrentUser
import tk.quietdev.level1.models.UserModel
import javax.inject.Inject

class RoomMapper @Inject constructor() {
    fun currentUserToUser (currentUser: CurrentUser) : UserModel {
        currentUser.apply {
            return UserModel(
                id = id,
                userName = name,
                email = email,
                occupation = career,
                physicalAddress = address,
                birthDate = birthday,
                phone = phone,
            )
        }
    }

}