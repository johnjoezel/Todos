package com.example.todos.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "todos")
data class Todo(
    val completed: Boolean,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 31,
    val todo: String,
    val userId: Int
)