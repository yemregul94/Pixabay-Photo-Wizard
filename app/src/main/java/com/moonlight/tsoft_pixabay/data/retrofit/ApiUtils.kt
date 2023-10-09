package com.moonlight.tsoft_pixabay.data.retrofit

import com.moonlight.tsoft_pixabay.util.BASE_URL

class ApiUtils {
    companion object{
        fun getPicturesDao(): PicturesDao{
            return RetrofitClient.getClient(BASE_URL).create(PicturesDao::class.java)
        }
    }
}