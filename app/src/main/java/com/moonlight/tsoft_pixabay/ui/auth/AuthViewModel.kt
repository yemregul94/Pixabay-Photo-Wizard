package com.moonlight.tsoft_pixabay.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.moonlight.tsoft_pixabay.data.repo.AuthRepository
import com.moonlight.tsoft_pixabay.data.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(var arepo: AuthRepository) : ViewModel() {

    private val _currentUser = MutableLiveData<FirebaseUser?>()
    val currentUser: LiveData<FirebaseUser?> = _currentUser

    var latestUser: FirebaseUser? = null

    var uid = currentUser.value?.uid

    init {
        getUser()
    }

    fun checkFirstLaunch() : Boolean{
        return arepo.checkFirstLaunch()
    }

    fun updateFirstLaunch(){
        arepo.updateFirstLaunch()
    }

    fun checkNewUser() : Boolean {
        return arepo.checkNewUser()
    }

    fun getUser(){
        _currentUser.value = arepo.getUser()
        getUid()
    }

    private fun getUid(){
        uid = currentUser.value?.uid
    }

    fun getLastLogin() : String?{
        return arepo.getLastLogin()
    }

    fun signOut(){
        arepo.signOut()
        getUser()
    }
}