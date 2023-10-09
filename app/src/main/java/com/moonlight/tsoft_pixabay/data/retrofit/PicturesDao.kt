package com.moonlight.tsoft_pixabay.data.retrofit

import com.moonlight.tsoft_pixabay.data.model.PicturesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PicturesDao {

    @GET("?")
    suspend fun searchPictures(@Query("q") searchQuery: String, @Query("page") page: Int, @Query("key") apikey: String, @Query("lang") language: String): PicturesResponse

    @GET("?")
    suspend fun getPicture(@Query("id") id: Int, @Query("key") apikey: String, @Query("lang") language: String): PicturesResponse


}