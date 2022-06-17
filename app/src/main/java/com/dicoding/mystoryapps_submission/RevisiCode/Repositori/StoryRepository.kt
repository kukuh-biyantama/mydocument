package com.dicoding.mystoryapps_submission.RevisiCode.Repositori

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.dicoding.mystoryapps_submission.RevisiCode.data.config.interfaceConfig.ApiServiceInterface
import com.dicoding.mystoryapps_submission.RevisiCode_Final.database.StoryDatabase
import com.dicoding.mystoryapps_submission.RevisiCode_Final.response.ListStoryItem


class StoryRepository(
    private val storiesDatabase: StoryDatabase,
    private val apiService: ApiServiceInterface
) {
    fun getStory(header: String): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
            ),
            pagingSourceFactory = {
                PagingSource(apiService, header)
            }
        ).liveData
    }
}