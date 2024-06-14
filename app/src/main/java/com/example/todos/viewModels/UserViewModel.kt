package com.example.todos.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todos.Repository
import com.example.todos.pojo.User
import kotlinx.coroutines.launch


class UserViewModel(private val repository: Repository): ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users



    private val _doneFetching = MutableLiveData<Boolean>()
    val doneFetching: LiveData<Boolean> get() = _doneFetching
    init {
        _doneFetching.value = false
    }


    fun setDoneFetching(fetched: Boolean) {
        _doneFetching.value = fetched
    }

    fun fetchUsers() {
        viewModelScope.launch {
            try {
                _users.postValue(repository.fetchAndStoreUsers())
            } catch (e: Exception) {
                Log.d("im here", "fetchUsers: ")
            }
        }
    }

}