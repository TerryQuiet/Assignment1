package tk.quietdev.level1.data.remote

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import tk.quietdev.level1.data.remote.models.ApiInternalUpdatedUser
import tk.quietdev.level1.data.remote.models.ApiUpdatedUser
import tk.quietdev.level1.data.remote.models.ApiUser
import tk.quietdev.level1.data.remote.models.ErrorRegResponse
import tk.quietdev.level1.domain.models.UserModel
import javax.inject.Inject

class RemoteMapper @Inject constructor() {


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