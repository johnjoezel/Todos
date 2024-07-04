package com.example.todos.domain.pojo

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    var userId: Int = 0,
    var email : String,
    var username: String,
    var password: String,
    var imgUri : Uri? = null,
    var isLoggedIn : Boolean = false
)