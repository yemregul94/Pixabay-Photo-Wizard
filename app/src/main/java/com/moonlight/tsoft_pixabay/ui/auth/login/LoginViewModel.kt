package com.moonlight.tsoft_pixabay.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moonlight.tsoft_pixabay.data.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(var arepo: AuthRepository) : ViewModel() {

    val errorMessage: LiveData<String> get() = _errorMessage
    private var _errorMessage: MutableLiveData<String> = MutableLiveData()

    suspend fun login(email: String, password: String){
        try {
            arepo.login(email, password)
        }catch (e: Exception){
            e.localizedMessage?.toString()?.let { sendError(it) }
        }
    }

    fun saveLastLogin(){
        arepo.saveLastLogin()
    }

    fun deleteLastLogin(){
        arepo.deleteLastLogin()
    }

    fun sendError(errorMessage: String){
        _errorMessage.value = errorMessage
    }
}