package com.example.todos.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.todos.pojo.Todo
import com.example.todos.pojo.TodoResponse
import com.example.todos.pojo.User

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTodos(todos: List<Todo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(todo: Todo) : Long

    @Query("SELECT * FROM todos WHERE userId = :userId")
    suspend fun getUserTodos(userId : Int): TodoResponse
}