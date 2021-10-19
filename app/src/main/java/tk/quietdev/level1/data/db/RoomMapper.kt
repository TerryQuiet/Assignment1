package tk.quietdev.level1.data.db

import tk.quietdev.level1.data.db.model.RoomCurrentUser
import tk.quietdev.level1.data.db.model.RoomUser
import tk.quietdev.level1.domain.models.UserModel
import javax.inject.Inject

class RoomMapper @Inject constructor() {
    fun roomUserToUser(roomCurrentUser: RoomUser): UserModel {
        roomCurrentUser.apply {
            return UserModel(
                id = id,
                userName = name,
                email = email,
                occupation = career,
                physicalAddress = address,
                birthDate = birthday?.take(10),
                phone = phone,
            )
        }
    }

    fun roomCurrentUserToUser(roomCurrentUser: RoomCurrentUser): UserModel {
        roomCurrentUser.apply {
            return UserModel(
                id = id,
                userName = name,
                email = email,
                occupation = career,
                physicalAddress = address,
                birthDate = birthday?.take(10),
                phone = phone,
            )
        }
    }

}