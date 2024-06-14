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

class TodoViewModel(private val repository: Repository):ViewModel() {
    private val _todos = MutableLiveData<List<Todo>>()
    val todos: LiveData<List<Todo>> = _todos

    fun fetchTodos() {
        viewModelScope.launch {
            try {
                _todos.postValue(repository.fetchandStoreTodos())
            } catch (e: Exception) {
                Log.d("im here", "fetchUsers: ")
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
//    fun observeToDoLiveData(): LiveData<List<Todo>> {
//        return todoListLiveData
//    }

}