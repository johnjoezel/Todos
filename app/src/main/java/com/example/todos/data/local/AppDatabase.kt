package com.example.todos.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todos.data.pojo.Todo
import com.example.todos.data.pojo.User

@Database(entities = [User::class, Todo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
    abstract fun todoDao() : TodoDao

}