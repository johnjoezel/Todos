package com.example.todos.domain.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todos.domain.converter.Converters
import com.example.todos.domain.pojo.Todo
import com.example.todos.domain.pojo.User

@Database(entities = [User::class, Todo::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
    abstract fun todoDao() : TodoDao

}