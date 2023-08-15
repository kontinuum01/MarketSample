package ru.gb.android.marketsample

import android.app.Application
import ru.gb.android.marketsample.layered.Injector as LayeredInjector
import ru.gb.android.marketsample.start.Injector as StartInjector

class MarketSampleApp: Application() {
    override fun onCreate() {
        super.onCreate()
        LayeredInjector.applicationContext = this
        StartInjector.applicationContext = this
    }
}
