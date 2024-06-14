package com.example.todos.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todos.UserRepository
import com.example.todos.pojo.User
import kotlinx.coroutines.launch


class UserViewModel(private val userRepository: UserRepository): ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    fun fetchUsers() {
        viewModelScope.launch {
            try {
                val userResponse = userRepository.fetchUsers()
                _users.value = userResponse.users
            } catch (e: Exception) {
                Log.d("im here", "fetchUsers: ")
            }
        }
    }
}