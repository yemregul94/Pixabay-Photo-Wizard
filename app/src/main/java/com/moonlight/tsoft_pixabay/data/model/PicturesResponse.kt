package com.moonlight.tsoft_pixabay.data.model

data class PicturesResponse (
    val total: Int,
    val totalHits: Int,
    val hits: List<Pictures>
)