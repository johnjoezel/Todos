package com.example.todos.viewmodels

import android.view.View
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todos.R
import com.example.todos.data.repositories.AuthRepository
import com.example.todos.data.pojo.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    val inputemail = MutableLiveData<String>()
    val inputusername = MutableLiveData<String>()
    val inputpassword = MutableLiveData<String>()


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



    fun signUp(){
        val email = inputemail.value
        val username = inputusername.value
        val password = inputpassword.value
        val user = User(email = email!!,username = username!!,password = password!!)
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