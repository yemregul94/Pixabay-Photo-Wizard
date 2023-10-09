package com.moonlight.tsoft_pixabay.data.repo

import com.google.firebase.auth.FirebaseUser
import com.moonlight.tsoft_pixabay.data.datasource.AuthDataSource

class AuthRepository (var ads: AuthDataSource) {

    fun getUser(): FirebaseUser? = ads.getUser()

    suspend fun login(email: String, password: String) = ads.login(email, password)

    suspend fun register(email: String, password: String) = ads.register(email, password)

    suspend fun resetPassword(email: String) = ads.resetPassword(email)

    fun checkFirstLaunch() : Boolean = ads.checkFirstLaunch()

    fun updateFirstLaunch() = ads.updateFirstLaunch()

    fun getLastLogin() : String? = ads.getLastLogin()

    fun saveLastLogin() = ads.saveLastLogin()

    fun deleteLastLogin() = ads.deleteLastLogin()

    fun checkNewUser() : Boolean = ads.checkNewUser()

    fun signOut() = ads.signOut()
}