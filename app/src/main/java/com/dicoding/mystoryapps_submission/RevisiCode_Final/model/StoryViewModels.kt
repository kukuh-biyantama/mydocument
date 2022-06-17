package com.dicoding.mystoryapps_submission.RevisiCode_Final.model

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.mystoryapps_submission.RevisiCode.Inject.Injection
import com.dicoding.mystoryapps_submission.RevisiCode.Repositori.StoryRepository
import com.dicoding.mystoryapps_submission.RevisiCode_Final.response.ListStoryItem

class StoryViewModels(private val storiesRepository: StoryRepository) : ViewModel() {

    fun stories(header: String): LiveData<PagingData<ListStoryItem>> =
        storiesRepository.getStory(header).cachedIn(viewModelScope)

    class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(StoryViewModels::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return StoryViewModels(Injection.provideRepository(context)) as T
            } else throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}