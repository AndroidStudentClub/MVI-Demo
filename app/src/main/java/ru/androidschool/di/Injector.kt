package ru.androidschool.di

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.androidschool.domain.roxie.ArticlesRepositoryImpl
import ru.androidschool.roxy.CustomViewModelFactory

class Injector(private val context: Context) {


    private fun provideRepository(): ArticlesRepositoryImpl {
        return ArticlesRepositoryImpl()
    }

    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return CustomViewModelFactory(provideRepository())
    }
}

interface InjectorProvider {
    val injector: Injector
}

fun Fragment.getInjector(): Injector {
    return (requireContext().applicationContext as InjectorProvider).injector
}