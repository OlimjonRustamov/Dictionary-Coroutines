package uz.olimjon_rustamov.dictionarycoroutines.App

import android.app.Application
import uz.olimjon_rustamov.dictionarycoroutines.Cashe.Cashe

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Cashe.init(this)
    }
}