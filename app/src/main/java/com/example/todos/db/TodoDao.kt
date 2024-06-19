package com.example.todos.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.todos.pojo.Todo
import com.example.todos.pojo.TodoResponse


@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTodos(todos: List<Todo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo)

    @Query("SELECT * FROM todos WHERE userId = :userId AND completed = 0")
    suspend fun getAvailableTodos(userId : Int): List<Todo>

    @Query("SELECT * FROM todos WHERE userId = :userId AND completed = 1")
    suspend fun getCompletedTodos(userId : Int): List<Todo>

    @Transaction
    @Delete
    suspend fun deleteTodo(todo : Todo)

    @Query("DELETE FROM todos")
    suspend fun clearTodoTable()

}