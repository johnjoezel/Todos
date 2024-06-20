package com.example.todos.apis

import com.example.todos.pojo.Todo
import com.example.todos.pojo.TodoResponse
import com.example.todos.pojo.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RemoteApi {

    @GET("users?limit=0")
    suspend fun getAllUsersFromApi() : UserResponse

    @GET("users/{userId}/todos")
    suspend fun getAllTodosFromApi(@Path("userId") userId : Int) : TodoResponse
}