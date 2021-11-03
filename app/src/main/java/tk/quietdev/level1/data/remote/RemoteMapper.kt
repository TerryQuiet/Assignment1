package tk.quietdev.level1.data.remote

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import tk.quietdev.level1.data.db.model.DeprecatedRoomCurrentUser
import tk.quietdev.level1.data.db.model.RoomUser
import tk.quietdev.level1.data.db.model.RoomUserContactsIds
import tk.quietdev.level1.data.remote.models.*
import tk.quietdev.level1.domain.models.UserModel
import javax.inject.Inject

class RemoteMapper @Inject constructor() {
    fun toRoomCurrentUser(userTokenData: UserTokenData): DeprecatedRoomCurrentUser {
        userTokenData.user.apply {
            return DeprecatedRoomCurrentUser(
                id = id,
                accessToken = userTokenData.accessToken,
                address = address,
                birthday = birthday,
                career = career,
                createdAt = createdAt,
                email = email,
                name = name,
                phone = phone,
                updatedAt = updatedAt,
            )
        }
    }

    fun apiUserToRoomUser(apiUser: ApiUser): RoomUser {
        apiUser.apply {
            return RoomUser(
                id = id,
                address = address,
                birthday = birthday,
                career = career,
                createdAt = createdAt,
                email = email,
                name = name,
                phone = phone,
                updatedAt = updatedAt,
            )
        }
    }

    fun apiUserToID(apiUser: ApiUser): RoomUserContactsIds {
        return RoomUserContactsIds(apiUser.id)
    }

    fun userToApiUserUpdate(userModel: UserModel): ApiUpdatedUser {
        userModel.apply {
            return ApiUpdatedUser(
                ApiInternalUpdatedUser(
                    name = userName,
                    phone = phone,
                    address = physicalAddress,
                    career = occupation,
                    birthday = birthDate,
                )
            )
        }
    }

    fun apiUserToDomainUser(apiUser: ApiUser): UserModel {
        with(apiUser) {
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

    fun shppApiErrorResponseMapper(): JsonAdapter<ErrorRegResponse> = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
        .adapter(ErrorRegResponse::class.java)
}