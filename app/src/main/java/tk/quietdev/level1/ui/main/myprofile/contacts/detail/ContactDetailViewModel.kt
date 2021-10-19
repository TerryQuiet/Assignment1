package tk.quietdev.level1.ui.main.myprofile.contacts.detail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import tk.quietdev.level1.domain.models.UserModel
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class ContactDetailViewModel @Inject constructor() : ViewModel() {
    var currentUserModelId by Delegates.notNull<Int>()
    lateinit var currentUserModel: UserModel
}