package tk.quietdev.level1.di

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import tk.quietdev.level1.database.FakeDatabase
import tk.quietdev.level1.ui.contacts.list.ContactListViewModel


val appModule = module {

    single { FakeDatabase(androidApplication().applicationContext) }

    viewModel { ContactListViewModel(get()) }
}