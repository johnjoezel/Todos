package com.example.todos.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todos.db.Repository
import com.example.todos.pojo.Todo
import com.example.todos.pojo.User
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: Repository, private val userId: Int):ViewModel() {

    private val _availableTodos = MutableLiveData<List<Todo>>()
    val availableTodos: LiveData<List<Todo>> = _availableTodos

    private val _completedTodos = MutableLiveData<List<Todo>>()
    val completedTodos: LiveData<List<Todo>> = _completedTodos

    init {
        viewModelScope.launch {
            repository.fetchTodos(userId)
        }
    }

    fun insertTodo(todo : Todo){
        viewModelScope.launch {
            try{
                repository.insertTodo(todo)
            } catch (e:Exception){
                throw e
            }
        }

    }

    fun deleteTodo(todo: Todo){
        viewModelScope.launch {
            try{
                repository.deleteTodo(todo)
            } catch (e:Exception){

            }

        }
    }

    fun getAvailableTodos(){
        viewModelScope.launch {
            try {
                _availableTodos.postValue(repository.getAvailableTodos(userId))
            } catch (e:Exception){
                throw e
            }
        }
    }

    fun getCompletedTodos(){
        viewModelScope.launch {
            try {
                _completedTodos.postValue(repository.getCompletedTodos(userId))
            } catch (e:Exception){
                throw e
            }
        }
    }
}