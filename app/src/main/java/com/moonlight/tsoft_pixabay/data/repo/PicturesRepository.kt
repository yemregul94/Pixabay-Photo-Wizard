package com.moonlight.tsoft_pixabay.data.repo

import com.moonlight.tsoft_pixabay.data.datasource.PicturesDataSource
import com.moonlight.tsoft_pixabay.data.model.Pictures
import com.moonlight.tsoft_pixabay.data.model.PicturesResponse

class PicturesRepository (var pds: PicturesDataSource) {

    suspend fun searchPictures(searchQuery: String, page: Int) : PicturesResponse = pds.searchPictures(searchQuery, page)

    suspend fun getPicture(id: Int) : Pictures = pds.getPicture(id)

}