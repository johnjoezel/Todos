package com.example.todos.data.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "todos",
    foreignKeys = [
        ForeignKey(entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE)
    ])
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val todo: String,
    val completed: Boolean,
    val userId: Int?
)