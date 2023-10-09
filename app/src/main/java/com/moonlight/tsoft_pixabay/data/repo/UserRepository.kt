package com.moonlight.tsoft_pixabay.data.repo

import androidx.lifecycle.MutableLiveData
import com.moonlight.tsoft_pixabay.data.datasource.UserDataSource
import com.moonlight.tsoft_pixabay.data.model.UserData

class UserRepository (var uds: UserDataSource) {

    fun updateUserData(user: UserData, uid: String?) = uds.updateUserData(user, uid)

    fun getUserData(uid: String?) : MutableLiveData<UserData?> = uds.getUserData(uid)
}