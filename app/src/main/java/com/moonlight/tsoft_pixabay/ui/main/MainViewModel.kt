package com.moonlight.tsoft_pixabay.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moonlight.tsoft_pixabay.data.model.UserData
import com.moonlight.tsoft_pixabay.data.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(var urepo: UserRepository) : ViewModel() {

    val user: LiveData<UserData?> get() = _user
    private var _user: MutableLiveData<UserData?> = MutableLiveData()

    fun updateUserData(user: UserData, uid: String?){
        urepo.updateUserData(user, uid)
    }

    fun getUser(uid: String?) {
        urepo.getUserData(uid).observeForever { userData ->
            _user.value = userData
        }
    }
}