package ru.androidschool.roxy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.androidschool.domain.roxie.ArticlesRepository

class CustomViewModelFactory(private val repository: ArticlesRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {

            modelClass.isAssignableFrom(RoxieViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return RoxieViewModel(repository) as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}