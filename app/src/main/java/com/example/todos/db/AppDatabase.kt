package com.example.todos.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todos.pojo.Todo
import com.example.todos.pojo.User

@Database(entities = [User::class, Todo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
    abstract fun todoDao() : TodoDao

}