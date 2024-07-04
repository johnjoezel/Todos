package com.example.todos.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todos.domain.repositories.Repository
import com.example.todos.domain.pojo.Todo
import com.example.todos.util.helper.SharedPreferenceHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(private val repository: Repository, private val sharedPreferenceHelper: SharedPreferenceHelper ):ViewModel() {

    val availableTodos : LiveData<List<Todo>> = repository.getAvailableTodos(sharedPreferenceHelper.userId)
    val completedTodos : LiveData<List<Todo>> = repository.getCompletedTodos(sharedPreferenceHelper.userId)

//    fun fetchTodoFromApiOneTime() {
//        viewModelScope.launch {
//            repository.fetchTodos(sharedPreferenceHelper.userId)
//        }
//    }

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