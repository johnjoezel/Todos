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

    @Query("SELECT * FROM todos WHERE completed = 0")
    fun getAvailableUserTodos(): LiveData<List<Todo>>

    @Query("SELECT * FROM todos WHERE completed = 1")
    fun getCompletedUserTodos(): LiveData<List<Todo>>

    @Query("SELECT * FROM todos")
    fun getTodos(): LiveData<List<Todo>>

    @Transaction
    @Delete
    suspend fun deleteTodo(todo : Todo)
}