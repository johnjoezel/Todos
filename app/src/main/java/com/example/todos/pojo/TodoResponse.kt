package com.example.todos.pojo

data class TodoResponse(
    val limit: Int,
    val skip: Int,
    val total: Int,
    val todos: List<Todo>
)