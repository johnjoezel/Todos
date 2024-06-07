package com.example.todos.apis

import com.example.todos.pojo.SignInRequestBody
import com.example.todos.pojo.User
import com.example.todos.pojo.Users
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {

    @GET("users")
    fun getAllUsers() : Call<Users>

    @POST("users/add")
    fun addUser(@Body user: User): Call<User>

    @POST("user/login")
    fun signInUser(@Body signInRequestBody: SignInRequestBody) : Call<User>
}