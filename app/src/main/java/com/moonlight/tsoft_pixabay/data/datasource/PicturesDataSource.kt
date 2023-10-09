package com.moonlight.tsoft_pixabay.data.datasource

import com.moonlight.tsoft_pixabay.data.model.Pictures
import com.moonlight.tsoft_pixabay.data.model.PicturesResponse
import com.moonlight.tsoft_pixabay.data.retrofit.PicturesDao
import com.moonlight.tsoft_pixabay.util.APIKEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale

class PicturesDataSource (var pdao: PicturesDao) {
    private val language = Locale.getDefault().language

    suspend fun searchPictures(searchQuery: String, page: Int) : PicturesResponse =
        withContext(Dispatchers.IO){
            pdao.searchPictures(searchQuery, page, APIKEY, language)
        }

    suspend fun getPicture(id: Int) : Pictures =
        withContext(Dispatchers.IO){
            pdao.getPicture(id, APIKEY, language).hits[0]
        }
}