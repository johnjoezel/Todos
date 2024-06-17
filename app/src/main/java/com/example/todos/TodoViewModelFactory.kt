package com.example.todos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todos.viewModels.TodoViewModel

class TodoViewModelFactory(private val repository: Repository, private val userId: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TodoViewModel(repository, userId) as T
    }
}
