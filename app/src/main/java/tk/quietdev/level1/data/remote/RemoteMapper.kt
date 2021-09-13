package tk.quietdev.level1.data.remote

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

import tk.quietdev.level1.data.db.model.RoomUser
import tk.quietdev.level1.data.db.model.RoomCurrentUser
import tk.quietdev.level1.data.db.model.RoomCurrentUserContacts
import tk.quietdev.level1.data.remote.models.ApiUser
import tk.quietdev.level1.data.remote.models.ErrorRegResponse
import tk.quietdev.level1.data.remote.models.UserTokenData
import tk.quietdev.level1.models.UserModel
import javax.inject.Inject

class RemoteMapper @Inject constructor() {
    fun toRoomCurrentUser(userTokenData: UserTokenData): RoomCurrentUser {
        userTokenData.user.apply {
            return RoomCurrentUser(
                id = id!!,
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

    fun apiUserToUser(apiUser: ApiUser): UserModel {
        apiUser.apply {
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

    fun userToApiUser(userModel: UserModel): ApiUser {
        userModel.apply {
            return ApiUser(
                id = id,
                address = physicalAddress,
                birthday = birthDate,
                career = occupation,
                email = email,
                name = userName,
                phone = phone,
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

    fun apiUserToID(apiUser: ApiUser) : RoomCurrentUserContacts {
        return RoomCurrentUserContacts(apiUser.id)
    }

    fun moshiErrorResponseMapper(): JsonAdapter<ErrorRegResponse> = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
        .adapter(ErrorRegResponse::class.java)
}