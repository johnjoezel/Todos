package com.example.todos.db

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.todos.apis.RemoteApi
import com.example.todos.pojo.Todo
import com.example.todos.pojo.User
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

class Repository @Inject constructor(private val todoDao: TodoDao, private val userDao: UserDao, private val remoteApi: RemoteApi) {

    val users: LiveData<User> = userDao.getUsers()

    suspend fun fetchAndStoreUsers(){
        if (users.value == null) {
            val usersFromApi = remoteApi.getAllUsersFromApi()
            val storeThereUsers = usersFromApi.map { mapToUser(it) }
            userDao.insertAll(storeThereUsers)
        }
    }

    private fun mapToUser(user: User): User {
        return User(
            userId = user.userId,
            firstName = user.firstName,
            lastName = user.lastName,
            gender = user.gender,
            email = user.email,
            username = user.username,
            password = user.password,
        )
    }

    suspend fun insertTodo(todo : Todo){
        return todoDao.insertTodo(todo)
    }

    private fun mapToTodo(todo: Todo) : Todo {
        return Todo(
            id = todo.id,
            todo = todo.todo,
            completed = todo.completed,
            userId = todo.userId
        )
    }
    suspend fun fetchTodos(userId: Int){
        try{
            val todos = todoDao.getUsersTodo(userId)
            if(todos.value == null){
                val todosFromApi = remoteApi.getAllTodosFromApi(userId)
                if(todosFromApi.isNotEmpty()){
                    val storeTheTodos = todosFromApi.map{ mapToTodo(it)}
                    todoDao.insertAllTodos(storeTheTodos)
                }
            }
        } catch (e: HttpException) {
            // new user can't be added to dummyjson
            Log.e(TAG, "HTTP error: ${e.code()}")
            // Handle specific error code if needed
        } catch (e: UnknownHostException) {
            // Handle network unavailable
            Log.e(TAG, "Network error: ${e.message}")
        } catch (e: Exception) {
            // Handle other errors
            Log.e(TAG, "Error fetching todos: ${e.message}", e)
        }
    }

    suspend fun deleteTodo(todo: Todo){
        todoDao.deleteTodo(todo)
    }

    fun getAvailableTodos(userId : Int) : LiveData<List<Todo>>{
        return todoDao.getAvailableTodos(userId)
    }
    fun getCompletedTodos(userId : Int) : LiveData<List<Todo>>{
        return todoDao.getCompletedTodos(userId)
    }

}