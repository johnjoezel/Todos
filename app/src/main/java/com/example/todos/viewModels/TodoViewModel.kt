package com.example.todos.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todos.AuthRepository
import com.example.todos.Repository
import com.example.todos.others.RetrofitInstance
import com.example.todos.pojo.Todo
import com.example.todos.pojo.TodoResponse
import com.example.todos.pojo.User
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TodoViewModel(private val repository: Repository, private val userId: Int):ViewModel() {


    private val _todos = MutableLiveData<List<Todo>>()
    val todos: LiveData<List<Todo>> get() = _todos

    fun fetchTodos() {
        viewModelScope.launch {
            try {
                _todos.postValue(repository.fetchandStoreTodos())
            } catch (e: Exception) {

            }
        }
    }

    fun fetchAvailableTodosByCurrentUser(){
        viewModelScope.launch {
            try {
                _todos.postValue(repository.fetchAvailableTodosByUserId(userId))
            } catch (e: Exception) {

            }
        }
    }

    fun fetchCompletedTodosByCurrentUser(userId : Int){
        viewModelScope.launch {
            try {
                _todos.postValue(repository.fetchCompletedTodosByUserId(userId))
            } catch (e: Exception) {

            }
        }
    }

    fun insertTodo(todo : Todo){
        viewModelScope.launch {
            try{
                repository.insertTodo(todo)
                fetchAvailableTodosByCurrentUser()
            } catch (e:Exception){

            }
        }

    }

//    fun deleteToDo(position: Int){
//        viewModelScope.launch {
//            val currentList = todoListLiveData.value.orEmpty().toMutableList()
//            currentList.removeAt(position)
//            todoListLiveData.postValue(currentList)
//        }
//    }

}