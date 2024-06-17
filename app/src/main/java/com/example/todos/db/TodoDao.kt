package com.example.todos.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.todos.pojo.Todo


@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTodos(todos: List<Todo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo) : Long

    @Query("SELECT * FROM todos WHERE userId = :userId AND completed = false")
    suspend fun getAvailableUserTodos(userId : Int): List<Todo>

    @Query("SELECT * FROM todos WHERE userId = :userId AND completed = true")
    suspend fun getCompletedUserTodos(userId : Int): List<Todo>
}