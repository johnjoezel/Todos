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
    fun getAvailableTodos(userId : Int): LiveData<List<Todo>>

    @Query("SELECT * FROM todos WHERE userId = :userId AND completed = 1")
    fun getCompletedTodos(userId : Int): LiveData<List<Todo>>

    @Query("SELECT * FROM todos WHERE userId = :userId")
    fun getUsersTodo(userId: Int) : LiveData<List<Todo>>
    @Transaction
    @Delete
    suspend fun deleteTodo(todo : Todo)

    @Query("DELETE FROM todos")
    suspend fun clearTodoTable()

}