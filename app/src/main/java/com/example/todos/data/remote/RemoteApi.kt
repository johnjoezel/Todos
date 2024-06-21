package com.example.todos.data.remote

import com.example.todos.data.pojo.Todo
import com.example.todos.data.pojo.User
import retrofit2.http.GET
import retrofit2.http.Path

interface RemoteApi {

    @GET("users?limit=0")
    suspend fun getAllUsersFromApi() : List<User>

    @GET("users/{userId}/todos")
    suspend fun getAllTodosFromApi(@Path("userId") userId : Int) : List<Todo>
}