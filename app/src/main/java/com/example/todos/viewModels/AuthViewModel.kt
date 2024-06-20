package com.example.todos.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todos.db.AuthRepository
import com.example.todos.pojo.User
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _loginUser = MutableLiveData<User>()
    val loginUser: LiveData<User> get() = _loginUser

    private val _returnLong = MutableLiveData<Long>()
    val returnLong: LiveData<Long> get() = _returnLong

    private val _usernameAvailable = MutableLiveData<Boolean>()
    val usernameAvailable: LiveData<Boolean> get() = _usernameAvailable


    fun signIn(username: String, password : String) {
        viewModelScope.launch {
            try {
                _loginUser.postValue(authRepository.signInUser(username, password))
            } catch (e: Exception) {

            }
        }
    }

    fun signUp(user : User){
        viewModelScope.launch {
            try{
                _returnLong.postValue(authRepository.insertUser(user))
            } catch (e:Exception){

            }
        }

    }

    fun checkUsernameAvailability(username : String){
        viewModelScope.launch {
            try{
                _usernameAvailable.postValue(authRepository.checkUsernameAvailability(username))
            } catch (e : Exception){

            }
        }
    }
}