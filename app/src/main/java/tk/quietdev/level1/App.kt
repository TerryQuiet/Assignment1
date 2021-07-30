package tk.quietdev.level1

import android.app.Application
import tk.quietdev.level1.database.FakeDatabase

import tk.quietdev.level1.utils.PrefsHelper
import tk.quietdev.level1.utils.ext.readAssetsFile


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        PrefsHelper.init(applicationContext)
        FakeDatabase.init(assets.readAssetsFile("json/FakeUserArray.json"))
    }
}