package com.example.todos.pojo

data class User(
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val username: String,
    val password: String,
    val refreshToken: String,
    val token: String

)

data class SignInRequestBody(
    val username: String,
    val password: String,
    val expiresInMins: Int = 60
)