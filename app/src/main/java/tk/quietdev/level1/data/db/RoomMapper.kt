package tk.quietdev.level1.data.db

import tk.quietdev.level1.data.db.model.RoomUser
import tk.quietdev.level1.models.UserModel
import javax.inject.Inject

class RoomMapper @Inject constructor() {
    fun roomUserToUser (roomCurrentUser: RoomUser) : UserModel {
        roomCurrentUser.apply {
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