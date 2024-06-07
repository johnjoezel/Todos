package com.example.todos.apis

import com.example.todos.pojo.Todo
import com.example.todos.pojo.TodoList
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TodoApi {

    @GET("todos")
    fun getAllToDos():Call<TodoList>

    @GET("todos/user/{userId}")
    fun getAllToDoByUserId(@Path("userId") userId : Int): Call<TodoList>

    @POST("todos/add")
    fun addToDo(@Body todo: Todo): Call<Todo>
}