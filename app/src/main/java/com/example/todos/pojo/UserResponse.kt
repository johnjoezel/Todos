package com.example.todos.pojo


data class UserResponse(
    val limit: Int,
    val skip: Int,
    val total: Int,
    val users: List<User>
)