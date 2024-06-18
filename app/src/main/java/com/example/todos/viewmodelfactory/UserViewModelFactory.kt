package com.example.todos.viewmodelfactory

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModel
import com.example.todos.db.Repository
import com.example.todos.viewModels.UserViewModel

class UserViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(repository) as T
    }
}
