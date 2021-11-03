package tk.quietdev.level1.data.db

import tk.quietdev.level1.data.db.model.DeprecatedRoomCurrentUser
import tk.quietdev.level1.data.db.model.RoomUser
import tk.quietdev.level1.domain.models.UserModel
import javax.inject.Inject

class RoomMapper @Inject constructor() {
    fun roomUserToDomainUser(roomCurrentUser: RoomUser): UserModel {
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

    @Deprecated("to remove")
    fun roomCurrentUserToDomainUser(roomCurrentUser: DeprecatedRoomCurrentUser): UserModel {
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

    fun domainUserToRoomUser(userModeuserModell: UserModel): RoomUser {
        with(userModeuserModell) {
            return RoomUser(
                email = email,
                name = userName,
                address = physicalAddress,
                birthday = birthDate,
                career = occupation,
                id = id,
                phone = phone
            )
        }

    }

}