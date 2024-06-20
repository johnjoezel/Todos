package com.example.todos.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todos.db.AuthRepository
import com.example.todos.viewModels.AuthViewModel

class AuthViewModelFactory(private val authRepository: AuthRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(authRepository) as T
    }
}