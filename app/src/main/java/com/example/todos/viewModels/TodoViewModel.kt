package com.example.todos.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todos.others.RetrofitInstance
import com.example.todos.pojo.Todo
import com.example.todos.pojo.TodoList
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TodoViewModel():ViewModel() {

    private val todoListLiveData = MutableLiveData<List<Todo>>()
    val todos: LiveData<List<Todo>> get() = todoListLiveData

    fun getAllTodo(){
        RetrofitInstance.api.getAllToDos().enqueue(object: Callback<TodoList> {
            override fun onResponse(call: Call<TodoList>, response: Response<TodoList>) {
                if(response.body()!= null){
                    todoListLiveData.value = response.body()?.todos
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<TodoList>, t: Throwable) {
                Log.d("PELYUR", t.message.toString())
            }
        })
    }
    fun getAllIncompleteByUserId(userId:Int){
        RetrofitInstance.api.getAllToDoByUserId(userId).enqueue(object: Callback<TodoList> {
            override fun onResponse(call: Call<TodoList>, response: Response<TodoList>) {
                if(response.isSuccessful){
                    val todoList = response.body()?.todos
                    val incompleteTodos = todoList?.filter { todo ->
                        !todo.completed
                    }
                    todoListLiveData.value = incompleteTodos!!
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<TodoList>, t: Throwable) {
                Log.d("PELYUR", t.message.toString())
            }
        })
    }

    fun getAllCompletedByUserId(userId:Int){
        RetrofitInstance.api.getAllToDoByUserId(userId).enqueue(object: Callback<TodoList> {
            override fun onResponse(call: Call<TodoList>, response: Response<TodoList>) {
                if(response.isSuccessful){
                    val todoList = response.body()?.todos
                    val completeTodos = todoList?.filter { todo ->
                        todo.completed
                    }
                    todoListLiveData.value = completeTodos!!
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<TodoList>, t: Throwable) {
                Log.d("PELYUR", t.message.toString())
            }
        })
    }

    fun addToDo(todo: Todo){
        RetrofitInstance.api.addToDo(todo).enqueue(object: Callback<Todo>{
            override fun onResponse(call: Call<Todo>, response: Response<Todo>) {
                if (response.isSuccessful) {
                    val addedTodo: Todo? = response.body()
                    addedTodo?.let {
                        val currentList = todoListLiveData.value.orEmpty().toMutableList()
                        currentList.add(addedTodo)
                        todoListLiveData.value = currentList.toList()
                    }

                } else {
                    // Handle error
                }
            }

            override fun onFailure(call: Call<Todo>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun deleteToDo(position: Int){
        viewModelScope.launch {
            val currentList = todoListLiveData.value.orEmpty().toMutableList()
            currentList.removeAt(position)
            todoListLiveData.postValue(currentList)
        }
    }
    fun observeToDoLiveData(): LiveData<List<Todo>> {
        return todoListLiveData
    }

}