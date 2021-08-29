package tk.quietdev.level1.database


import android.content.Context
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import dagger.hilt.android.qualifiers.ApplicationContext
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.models.convertors.Convertor
import tk.quietdev.level1.utils.ContactsFetcher
import tk.quietdev.level1.utils.ext.readAssetsFile
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeDatabase @Inject constructor(
    @ApplicationContext context: Context,
    private val contactsFetcher: ContactsFetcher,
    private val convertor: Convertor
) {

    private var userIds: Int = 0

    // all the users in database, using Int as ID
    private val allFakeUsers: MutableMap<Int, UserModel> = emptyMap<Int, UserModel>().toMutableMap()


    init {
        addUsersFromJson(context.assets.readAssetsFile("json/FakeUserArray.json"))
        //addUsersFromPhone()
    }


    var currentUserID: Int? = null

    private fun isPasswordCorrect(userModel: UserModel?, password: String): Boolean {
        return (userModel?.password == password)
    }


    private fun addUsersFromJson(string: String) {
        val mapper = jacksonObjectMapper()
        val userModelList: List<UserModel> = mapper.readValue(string)
        userModelList.forEach { addUser(it) }
    }

    private fun addUsersFromPhone() {
        contactsFetcher.fetchContacts().forEach { contact ->
            convertor.convertContactToUser(contact)?.let { user ->
                addUser(user)
            }
        }
    }

    /**
     * @param amount the number of users to return, -1 if all
     * @return list of users
     */
    fun getUserList(amount: Int = -1): List<UserModel> {
        return allFakeUsers.values.take(if (amount < 0) allFakeUsers.size else amount)
    }

    fun getUserWithValidation(email: String, password: String): UserModel? {
        val id = findIdByEmail(email)
        return if (isPasswordCorrect(allFakeUsers[id], password)) {
            allFakeUsers[id]
        } else null
    }

    private fun findIdByEmail(email: String): Int? {
       return allFakeUsers.values.find { it.email == email }?._id
    }

    fun getUserWithNoValidation(id: Int): UserModel? {
        return allFakeUsers[id]
    }


    /**
     * @return user with Id assigned.
     * this should always be used when user is created.
     */
    fun addUser(userModel: UserModel): UserModel {
        userModel.apply {
            _id = userIds++
            _id?.let {
                allFakeUsers[it] = this
            }
            return this
        }
    }

    fun updateUser(updatedUserModel: UserModel) {
        allFakeUsers[updatedUserModel._id!!] = updatedUserModel
    }

}