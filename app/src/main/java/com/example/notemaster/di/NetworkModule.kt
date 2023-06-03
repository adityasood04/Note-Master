package com.example.notemaster.di

import com.example.notemaster.api.UserApi
import com.example.notemaster.utils.Constants
import com.example.notemaster.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit():Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }




    @Singleton
    @Provides
    fun provideUserApi(retrofit: Retrofit):UserApi{
        return retrofit.create(UserApi::class.java)
    }
}