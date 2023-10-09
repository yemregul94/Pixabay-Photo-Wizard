package com.moonlight.tsoft_pixabay.data.datasource

import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthDataSource @Inject constructor(var sp: SharedPreferences){

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var user = auth.currentUser
    private var isNewUser = false

    init {
        auth.useAppLanguage()
    }

    fun getUser() : FirebaseUser? {
        user = auth.currentUser
        return user
    }

    suspend fun login(email:String, password:String){
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                isNewUser = task.result.additionalUserInfo?.isNewUser!!
            }
        }.addOnFailureListener {

        }.await()

    }

    suspend fun register(email: String, password: String){
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if(task.isSuccessful) {
                saveLastLogin()
                isNewUser = task.result.additionalUserInfo?.isNewUser!!
            }
        }.addOnFailureListener {

        }.await()
    }

    suspend fun resetPassword(email: String){
        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful){

            }
        }.addOnFailureListener {

        }.await()
    }

    fun checkFirstLaunch() : Boolean{
        return sp.getBoolean("firstLaunch", true)
    }

    fun updateFirstLaunch(){
        sp.edit().putBoolean("firstLaunch", false).apply()
    }

    fun saveLastLogin(){
        getUser()
        sp.edit().putString("email", user?.email).apply()
    }

    fun deleteLastLogin(){
        sp.edit().putString("email", "").apply()
    }

    fun getLastLogin() : String?{
        return sp.getString("email", "")
    }

    fun checkNewUser() : Boolean{
        return isNewUser
    }

    fun signOut(){
        auth.signOut()
    }
}