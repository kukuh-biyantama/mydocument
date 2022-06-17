package com.dicoding.mystoryapps_submission.RevisiCode_Final.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.mystoryapps_submission.RevisiCode_Final.pref.UserPreference
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class ViewModels(private val pref: UserPreference) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}

class Executors {
    val diskIO: Executor = Executors.newSingleThreadExecutor()
}