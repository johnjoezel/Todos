package com.example.todos

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModel
import com.example.todos.viewModels.UserViewModel

class UserViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(repository) as T
    }
}
