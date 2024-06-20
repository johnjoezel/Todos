package com.example.todos.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Int = 0,
    val email: String,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val username: String,
    val password: String
)

data class SignInRequestBody(
    val username: String,
    val password: String,
    val expiresInMins: Int = 60
)