package com.example.todos.data.remote

import com.example.todos.data.pojo.Todo
import com.example.todos.data.pojo.TodoResponse
import com.example.todos.data.pojo.User
import com.example.todos.data.pojo.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RemoteApi {

    @GET("users?limit=0")
    suspend fun getAllUsersFromApi() : UserResponse

//    @GET("users/{userId}/todos")
//    suspend fun getAllTodosFromApi(@Path("userId") userId : Int) : TodoResponse

//    @POST("todos")
//    suspend fun insertTodo(@Body todo : Todo)
}