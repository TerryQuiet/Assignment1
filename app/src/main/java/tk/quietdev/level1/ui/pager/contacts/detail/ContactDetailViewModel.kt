package tk.quietdev.level1.ui.pager.contacts.detail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import tk.quietdev.level1.models.UserModel
import javax.inject.Inject

@HiltViewModel
class ContactDetailViewModel @Inject constructor() : ViewModel() {
    lateinit var currentUserModel: UserModel
}