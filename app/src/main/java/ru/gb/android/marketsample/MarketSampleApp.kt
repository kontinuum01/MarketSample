package ru.gb.android.marketsample

import android.app.Application
import ru.gb.android.marketsample.layered.Injector as LayeredInjector
import ru.gb.android.marketsample.start.Injector as StartInjector
import ru.gb.android.marketsample.clean.Injector as CleanInjector

class MarketSampleApp: Application() {
    override fun onCreate() {
        super.onCreate()
        LayeredInjector.applicationContext = this
        StartInjector.applicationContext = this
        CleanInjector.applicationContext = this
    }
}
