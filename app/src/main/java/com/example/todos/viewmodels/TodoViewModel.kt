package com.example.todos.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todos.data.repositories.Repository
import com.example.todos.data.pojo.Todo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(private val repository: Repository, private val userId: Int):ViewModel() {

    val availableTodos : LiveData<List<Todo>> = repository.getAvailableTodos(userId)
    val completedTodos : LiveData<List<Todo>> = repository.getCompletedTodos(userId)

    fun fetchTodoFromApiOneTime() {
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
}