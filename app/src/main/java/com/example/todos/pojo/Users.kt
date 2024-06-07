package com.example.todos.pojo


data class Users(
    val limit: Int,
    val skip: Int,
    val total: Int,
    val users: List<User>
)