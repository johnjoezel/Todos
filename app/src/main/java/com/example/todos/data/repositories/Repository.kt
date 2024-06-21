package com.example.todos.data.repositories

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.todos.MainApplication
import com.example.todos.data.remote.RemoteApi
import com.example.todos.data.pojo.Todo
import com.example.todos.data.pojo.User
import com.example.todos.data.local.TodoDao
import com.example.todos.data.local.UserDao
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

class Repository @Inject constructor(private val todoDao: TodoDao, private val userDao: UserDao, private val remoteApi: RemoteApi) {

    val users: LiveData<User> = userDao.getUsers()

    suspend fun fetchAndStoreUsers(){
        if (users.value == null) {
            try{
                val usersFromApi = remoteApi.getAllUsersFromApi()
                val storeThereUsers = usersFromApi.users.map { mapToUser(it) }
                userDao.insertAll(storeThereUsers)
            } catch (e:Exception){
                MainApplication.showToastMessage(e.message.toString())
            }

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
                val storeTheTodos = todosFromApi.todos.map{ mapToTodo(it)}
                todoDao.insertAllTodos(storeTheTodos)
            }
        } catch (e: HttpException) {
            MainApplication.showToastMessage(e.message.toString())
        } catch (e: UnknownHostException) {
            // Handle network unavailable
            MainApplication.showToastMessage(e.message.toString())
        } catch (e: Exception) {
            // Handle other errors
            MainApplication.showToastMessage(e.message.toString())
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