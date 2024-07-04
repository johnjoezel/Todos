package com.example.todos.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todos.MainApplication
import com.example.todos.domain.repositories.Repository
import com.example.todos.domain.pojo.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    private val _user = MutableLiveData<User>()
    val user : LiveData<User> = _user

    fun fetchUsers() {
        viewModelScope.launch {
            try {
               repository.fetchAndStoreUsers()
            } catch (e: Exception) {
                MainApplication.showToastMessage(e.message.toString())
            }
        }
    }

    fun fetchUser(userId : Int){
        viewModelScope.launch {
            _user.postValue(repository.fetchUser(userId))
        }
    }

    fun updateUser(userId : Int, imageUri : String?){
        val thisimageUri = imageUri?.let { Uri.parse(it) }
        viewModelScope.launch {
            repository.updateUser(userId, thisimageUri)
        }
    }

}