package com.moonlight.tsoft_pixabay.di

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.moonlight.tsoft_pixabay.data.datasource.AuthDataSource
import com.moonlight.tsoft_pixabay.data.datasource.PicturesDataSource
import com.moonlight.tsoft_pixabay.data.datasource.UserDataSource
import com.moonlight.tsoft_pixabay.data.repo.AuthRepository
import com.moonlight.tsoft_pixabay.data.repo.PicturesRepository
import com.moonlight.tsoft_pixabay.data.repo.UserRepository
import com.moonlight.tsoft_pixabay.data.retrofit.ApiUtils
import com.moonlight.tsoft_pixabay.data.retrofit.PicturesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideAuthRepository(ads: AuthDataSource) : AuthRepository {
        return AuthRepository(ads)
    }

    @Provides
    @Singleton
    fun provideAuthDataSource(sp: SharedPreferences) = AuthDataSource(sp)

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("login", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideUserRepository(uds: UserDataSource) : UserRepository {
        return UserRepository(uds)
    }

    @Provides
    @Singleton
    fun provideUserDataSource(refDB: DatabaseReference, sp: SharedPreferences) : UserDataSource {
        return UserDataSource(refDB, sp)
    }

    @Provides
    @Singleton
    fun provideUserDatabaseReference() : DatabaseReference {
        val db = FirebaseDatabase.getInstance()
        db.setPersistenceEnabled(true)
        return db.reference
    }

    @Provides
    @Singleton
    fun providePicturesRepository(pds: PicturesDataSource) : PicturesRepository {
        return PicturesRepository(pds)
    }

    @Provides
    @Singleton
    fun providePicturesDataSource(pdao: PicturesDao) : PicturesDataSource {
        return PicturesDataSource(pdao)
    }

    @Provides
    @Singleton
    fun providePicturesDao(): PicturesDao {
        return ApiUtils.getPicturesDao()
    }

}