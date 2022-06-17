package com.dicoding.mystoryapps_submission.RevisiCode_Final.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.mystoryapps_submission.RevisiCode_Final.model.Auth
import com.dicoding.mystoryapps_submission.RevisiCode_Final.pref.UserPreference
import kotlinx.coroutines.launch

class MainViewModel(private val pref: UserPreference) : ViewModel() {
    fun getUser(): LiveData<Auth> {
        return pref.getUser().asLiveData()
    }

    fun saveUser(user: Auth) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }
}