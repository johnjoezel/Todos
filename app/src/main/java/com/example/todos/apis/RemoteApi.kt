package com.example.todos.apis

import com.example.todos.pojo.Todo
import com.example.todos.pojo.TodoResponse
import com.example.todos.pojo.UserResponse
import retrofit2.http.GET

interface RemoteApi {

    @GET("users?limit=0")
    suspend fun getAllUsersFromApi() : UserResponse

    @GET("todos?limit=0")
    suspend fun getAllTodosFromApi() : TodoResponse


}