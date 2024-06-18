package com.example.todos.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todos.db.Repository
import com.example.todos.pojo.Todo
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: Repository, private val userId: Int):ViewModel() {

    val availableTodos: LiveData<List<Todo>> = repository.availableTodos
    val completedTodos: LiveData<List<Todo>> = repository.completedTodos

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

}