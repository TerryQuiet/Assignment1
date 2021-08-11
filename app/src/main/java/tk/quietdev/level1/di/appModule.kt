package tk.quietdev.level1.di

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import tk.quietdev.level1.database.FakeDatabase
import tk.quietdev.level1.models.convertors.Convertor
import tk.quietdev.level1.ui.authorization.AuthViewModel
import tk.quietdev.level1.ui.pager.AppbarSharedViewModel
import tk.quietdev.level1.ui.pager.contacts.ContactsSharedViewModel
import tk.quietdev.level1.ui.pager.contacts.detail.ContactDetailViewModel
import tk.quietdev.level1.ui.pager.contacts.dialog.AddContactViewModel
import tk.quietdev.level1.ui.pager.contacts.edit.EditProfileViewModel
import tk.quietdev.level1.ui.pager.contacts.list.ContactListViewModel
import tk.quietdev.level1.ui.pager.settings.SettingsViewModel
import tk.quietdev.level1.utils.ContactsFetcher


val appModule = module {
    single { ContactsFetcher(androidApplication()) }
    single { Convertor() }
    single { FakeDatabase(androidApplication().applicationContext, get(), get()) }

    viewModel { ContactListViewModel(get()) }
    viewModel { AddContactViewModel(get()) }
    viewModel { ContactsSharedViewModel() }
    viewModel { ContactDetailViewModel() }
    viewModel { EditProfileViewModel(get()) }
    viewModel { SettingsViewModel() }
    viewModel { AuthViewModel(get()) }
    viewModel { AppbarSharedViewModel() }
}