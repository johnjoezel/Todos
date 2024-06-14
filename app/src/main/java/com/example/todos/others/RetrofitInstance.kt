package com.example.todos.others

import com.example.todos.apis.TodoApi
import com.example.todos.apis.RemoteApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api: TodoApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TodoApi::class.java)
    }
    val userApi: RemoteApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RemoteApi::class.java)
    }
}