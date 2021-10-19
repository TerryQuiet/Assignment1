package tk.quietdev.level1.ui.main.navigation

import tk.quietdev.level1.domain.models.UserModel
import tk.quietdev.level1.ui.main.myprofile.contacts.pager.ViewPagerType

interface Navigator {
    fun openEditProfileFragment(userModel: UserModel)
    fun openViewPagerContainerFragment(userId: Int, pagerType: ViewPagerType)
    fun openContactDetailFragment(userModel: UserModel)
    fun openAddContactsListFragment()
    fun openRemoveContactsListFragment()

}