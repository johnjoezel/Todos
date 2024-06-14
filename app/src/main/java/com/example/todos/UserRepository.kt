package com.example.todos

import com.example.todos.apis.RemoteApi
import com.example.todos.pojo.UserResponse

class UserRepository(private val remoteApi: RemoteApi) {

    suspend fun fetchUsers(): UserResponse {
        return remoteApi.getAllUsers()
    }
}