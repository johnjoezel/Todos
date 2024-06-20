package com.example.todos.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todos.db.Repository
import com.example.todos.viewModels.TodoViewModel

class TodoViewModelFactory(private val repository: Repository, private val userId: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TodoViewModel(repository, userId) as T
    }
}
