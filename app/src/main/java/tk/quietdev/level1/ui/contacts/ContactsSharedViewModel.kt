package tk.quietdev.level1.ui.contacts


import androidx.lifecycle.ViewModel


/**
 * Here I have list of Emails to show in recycleView (I'm not showing all I have in DB)
 */

class ContactsSharedViewModel : ViewModel() {
/*

    fun onDialogAddClicked(dialogBinding: DialogAddContactBinding) {
        val name = dialogBinding.etName.text.toString()
        val surname = dialogBinding.etSurname.text.toString()
        val occupation = dialogBinding.etOccupation.text.toString()

        val user = User(
            userName = "$name $surname",
            email = "$name.$surname@mail.fake",
            occupation = occupation
        )
        addNewUserToDatabase(user)
    }

    fun setUserRecoverable(email: String, boolean: Boolean) {
        deletedUsers[email] = boolean
    }

    fun updateUser(oldUserID: String, user: User) {
        db.updateUser(oldUserID, user)
        internalListUpdate(oldUserID, user)
        userList.postValue(userList.value)
    }

    */
/**
     * If email was updated, we have to delete replace it with new
     *//*

    private fun internalListUpdate(oldUserID: String, user: User) {
        if (oldUserID != user.email) {
            removeUser(oldUserID)
            userList.value?.add(deletedUserPosition, user.email)
        }
    }

    private fun updateLiveData() {
        userList.postValue(userList.value)
    }

*/


}