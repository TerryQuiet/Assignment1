package tk.quietdev.level1.ui.contacts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import tk.quietdev.level1.database.FakeDatabase
import tk.quietdev.level1.databinding.DialogAddContactBinding
import tk.quietdev.level1.models.User

import tk.quietdev.level1.utils.OnSwipeCallBack


class ContactsViewModel : ViewModel(), OnSwipeCallBack.Listener {


    private var deletedUserPosition = 0

    val userList = MutableLiveData(FakeDatabase.userContacts)
    val deletedUser = MutableLiveData("")


    private fun updateLiveData() {
        userList.value = FakeDatabase.userContacts
    }


    fun addUserBack() {
        if (deletedUser.value.toString().isNotEmpty()) {
            FakeDatabase.userContacts.add(deletedUserPosition, deletedUser.value.toString())
            updateLiveData()
            deletedUser.value = ""
        }
    }

    private fun addNewUserToDatabase(user: User) {
        FakeDatabase.allFakeUsers[user.email] = user
        FakeDatabase.userContacts.add(user.email)
        updateLiveData()
    }

    fun removeUser(email: String?) {
        val position = FakeDatabase.userContacts.indexOf(email)
        deletedUser.value = FakeDatabase.userContacts.removeAt(position)
        deletedUserPosition = position
        updateLiveData()
    }

    override fun swipedOn(viewHolder: RecyclerView.ViewHolder) {
        val email = (viewHolder as RecycleViewAdapter2.RowHolder).email
        removeUser(email)
    }

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



}