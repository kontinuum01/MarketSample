package ru.gb.android.marketsample

import android.app.Application
import ru.gb.android.marketsample.start.ServiceLocator as StartServiceLocator
import ru.gb.android.marketsample.layered.ServiceLocator as LayeredServiceLocator
import ru.gb.android.marketsample.clean.ServiceLocator as CleanServiceLocator

class MarketSampleApp: Application() {
    override fun onCreate() {
        super.onCreate()
        LayeredServiceLocator.applicationContext = this
        StartServiceLocator.applicationContext = this
        CleanServiceLocator.applicationContext = this
    }
}
