package com.dicoding.mystoryapps_submission.RevisiCode.Inject

import android.content.Context
import com.dicoding.mystoryapps_submission.RevisiCode.Repositori.StoryRepository
import com.dicoding.mystoryapps_submission.RevisiCode.data.config.interfaceConfig.ApiConfig
import com.dicoding.mystoryapps_submission.RevisiCode_Final.database.StoryDatabase


object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val database = StoryDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()

        return StoryRepository(database, apiService)
    }
}