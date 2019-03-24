package michalengel.pwr.application2

import android.app.Application
import michalengel.pwr.application2.di.groupModule
import org.koin.android.ext.android.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(groupModule))
    }


}