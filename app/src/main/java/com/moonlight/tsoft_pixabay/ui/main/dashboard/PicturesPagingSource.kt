package com.moonlight.tsoft_pixabay.ui.main.dashboard

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.moonlight.tsoft_pixabay.data.model.Pictures
import com.moonlight.tsoft_pixabay.data.repo.PicturesRepository
import retrofit2.HttpException
import java.lang.Exception

class PicturesPagingSource(private val prepo: PicturesRepository, private val query: String) : PagingSource<Int, Pictures>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pictures> {
        return try {
            val currentPage = params.key ?: 1
            val response = prepo.searchPictures(query, currentPage)
            val data = response.hits
            if (data.isEmpty()) {
                return LoadResult.Error(Throwable("No Data Loaded"))
            }

            val responseData = mutableListOf<Pictures>()
            responseData.addAll(data)

            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (httpE: HttpException) {
            LoadResult.Error(httpE)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Pictures>): Int? {
        return null
    }
}