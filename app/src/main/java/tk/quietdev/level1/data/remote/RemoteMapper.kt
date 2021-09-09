package tk.quietdev.level1.data.remote

import tk.quietdev.level1.data.db.model.CurrentUser
import tk.quietdev.level1.data.remote.models.UserTokenData
import javax.inject.Inject

class RemoteMapper @Inject constructor() {
    fun mapRemoteAuthToRoomCurrentUser(userTokenData: UserTokenData): CurrentUser {
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
}