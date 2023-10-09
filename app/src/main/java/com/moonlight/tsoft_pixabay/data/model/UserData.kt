package com.moonlight.tsoft_pixabay.data.model

import java.io.Serializable

data class UserData(
    var uid: String = "",
    var userName: String = "",
    var favList: List<Int> = emptyList()
) : Serializable