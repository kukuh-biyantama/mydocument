package com.dicoding.mystoryapps_submission.RevisiCode.Repositori

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dicoding.mystoryapps_submission.RevisiCode.data.config.interfaceConfig.ApiServiceInterface
import com.dicoding.mystoryapps_submission.RevisiCode_Final.response.ListStoryItem


class PagingSource(private val apiService: ApiServiceInterface, private val header: String) : PagingSource<Int, ListStoryItem>() {

    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getStories(header, page, params.loadSize)

            LoadResult.Page(
                data = responseData.listStory,
                prevKey = if(page == INITIAL_PAGE_INDEX) null else page - 1,
                nextKey = if(responseData.listStory.isNullOrEmpty()) null else page + 1
            )
        } catch(exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}