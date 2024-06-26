package com.example.todos.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todos.data.converter.Converters
import com.example.todos.data.pojo.Todo
import com.example.todos.data.pojo.User

@Database(entities = [User::class, Todo::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
    abstract fun todoDao() : TodoDao

}