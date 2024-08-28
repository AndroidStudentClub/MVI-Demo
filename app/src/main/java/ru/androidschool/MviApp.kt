package ru.androidschool

import android.app.Application
import ru.androidschool.di.Injector
import ru.androidschool.di.InjectorProvider

class MviApp : Application(), InjectorProvider {

    private lateinit var _injector: Injector

    override val injector: Injector
        get() = _injector

    override fun onCreate() {
        super.onCreate()

        _injector = Injector(this)
    }
}