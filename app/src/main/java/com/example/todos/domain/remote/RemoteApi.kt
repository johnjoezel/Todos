package com.example.todos.domain.remote

import com.example.todos.domain.pojo.UserResponse
import retrofit2.http.GET

interface RemoteApi {

    @GET("users?limit=0")
    suspend fun getAllUsersFromApi() : UserResponse

//    @GET("users/{userId}/todos")
//    suspend fun getAllTodosFromApi(@Path("userId") userId : Int) : TodoResponse

//    @POST("todos")
//    suspend fun insertTodo(@Body todo : Todo)
}