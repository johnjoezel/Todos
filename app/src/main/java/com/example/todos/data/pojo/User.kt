package com.example.todos.data.pojo

import android.graphics.Bitmap
import android.net.Uri
import androidx.room.ColumnInfo
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