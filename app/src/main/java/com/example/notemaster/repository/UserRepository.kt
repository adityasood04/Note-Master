package com.example.notemaster.repository

import com.example.notemaster.api.UserApi
import com.example.notemaster.models.UserRequest
import com.example.notemaster.models.UserResponse
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApi: UserApi) {


    suspend fun registerUser(userRequest: UserRequest){
        val response = userApi.signUp(userRequest)
    }
    suspend fun loginUser(userRequest: UserRequest){
        val response = userApi.signIn(userRequest)
    }
}