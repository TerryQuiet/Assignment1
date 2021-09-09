package tk.quietdev.level1.data.remote

import tk.quietdev.level1.data.db.model.CurrentUser
import tk.quietdev.level1.data.remote.models.UserTokenData
import tk.quietdev.level1.models.UserModel
import javax.inject.Inject

class RemoteMapper @Inject constructor() {
    fun toRoomCurrentUser(userTokenData: UserTokenData): CurrentUser {
        userTokenData.user.apply {
            return CurrentUser(
                id = id,
                address = address,
                birthday = birthday,
                career = career,
                createdAt = createdAt,
                email = email,
                name = name,
                phone = phone,
                updatedAt = updatedAt,
                accessToken = userTokenData.accessToken
            )
        }
    }

    fun toUser(userTokenData: UserTokenData): UserModel {
        userTokenData.user.apply {
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