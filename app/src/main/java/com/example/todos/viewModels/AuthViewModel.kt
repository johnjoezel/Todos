package com.example.todos.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todos.AuthRepository
import com.example.todos.pojo.User
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _loginUser = MutableLiveData<User>()
    val loginUser: LiveData<User> get() = _loginUser

    private val _returnLong = MutableLiveData<Long>()
    val returnLong: LiveData<Long> get() = _returnLong

    fun signIn(username: String, password : String) {
        viewModelScope.launch {
            try {
                _loginUser.postValue(authRepository.signInUser(username, password))
            } catch (e: Exception) {
                Log.d("im here", "fetchUsers: ")
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
}