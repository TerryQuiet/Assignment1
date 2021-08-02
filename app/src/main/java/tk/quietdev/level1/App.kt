package tk.quietdev.level1

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import tk.quietdev.level1.di.appModule
import tk.quietdev.level1.utils.PrefsHelper


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        PrefsHelper.init(applicationContext)

        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }
}