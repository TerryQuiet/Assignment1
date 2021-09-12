package tk.quietdev.level1.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import tk.quietdev.level1.data.db.model.AllUsers
import tk.quietdev.level1.data.db.model.CurrentUser
import tk.quietdev.level1.data.remote.models.ApiUser
import tk.quietdev.level1.data.remote.models.ErrorRegResponse
import tk.quietdev.level1.data.remote.models.UserTokenData
import tk.quietdev.level1.models.UserModel
import javax.inject.Inject

class RemoteMapper @Inject constructor() {
    fun toRoomCurrentUser(userTokenData: UserTokenData): CurrentUser {
        userTokenData.user.apply {
            return CurrentUser(
                id = id!!,
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
                address = physicalAddress,
                birthday = birthDate,
                career = occupation,
                email = email,
                name = userName,
                phone = phone,
            )
        }
    }

    fun apiUserToAllUsers(apiUser: ApiUser): AllUsers {
        apiUser.apply {
            return AllUsers(
                id = id!!,
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

    fun moshiErrorResponseMapper() = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
        .adapter(ErrorRegResponse::class.java)
}