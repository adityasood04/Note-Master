package com.example.notemaster

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notemaster.models.UserRequest
import com.example.notemaster.models.UserResponse
import com.example.notemaster.repository.UserRepository
import com.example.notemaster.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor (private val userRepository: UserRepository):ViewModel() {


    val userResponseLiveData : LiveData<NetworkResult<UserResponse>>
        get() = userRepository.userResponseLiveData
    fun registerUser(userRequest: UserRequest){
        viewModelScope.launch {
            userRepository.registerUser(userRequest)
        }
    }
    fun loginUser(userRequest: UserRequest) {
        viewModelScope.launch {
            userRepository.loginUser(userRequest)
        }
    }

}