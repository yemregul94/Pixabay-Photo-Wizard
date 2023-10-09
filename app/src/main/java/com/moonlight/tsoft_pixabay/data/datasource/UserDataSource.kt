package com.moonlight.tsoft_pixabay.data.datasource

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.moonlight.tsoft_pixabay.data.model.UserData
import javax.inject.Inject

class UserDataSource @Inject constructor(var refDB: DatabaseReference, var sp: SharedPreferences) {

    fun updateUserData(user: UserData, uid: String?){
        if(!uid.isNullOrEmpty()) {
            refDB.child("PixabayUsers").child(uid).child("UserData").setValue(user)
                .addOnCompleteListener {
                    if (it.isSuccessful) {

                    }
                }.addOnFailureListener {
                }
        }
    }


    fun getUserData(uid: String?) : MutableLiveData<UserData?> {

        val userLiveData = MutableLiveData<UserData?>()
        if(!uid.isNullOrEmpty()) {
            refDB.child("PixabayUsers").child(uid).child("UserData").addValueEventListener(object :
                ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val user = dataSnapshot.getValue(UserData::class.java)
                    userLiveData.value = user
                }

                override fun onCancelled(databaseError: DatabaseError) {
                }

            })
        }
        return userLiveData
    }
}