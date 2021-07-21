package tk.quietdev.level1

import android.app.Application
import tk.quietdev.level1.database.MockDatabase

import tk.quietdev.level1.utils.PrefsHelper


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        PrefsHelper.init(applicationContext)
        MockDatabase.init()
    }
}