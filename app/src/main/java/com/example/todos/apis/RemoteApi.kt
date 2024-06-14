package com.example.todos.apis

import com.example.todos.pojo.UserResponse
import retrofit2.http.GET

interface RemoteApi {

    @GET("users")
    suspend fun getAllUsers() : UserResponse

}