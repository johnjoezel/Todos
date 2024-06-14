package com.example.todos

import android.util.Log
import androidx.room.PrimaryKey
import com.example.todos.apis.RemoteApi
import com.example.todos.db.TodoDao
import com.example.todos.db.UserDao
import com.example.todos.pojo.Todo
import com.example.todos.pojo.User

class Repository(private val todoDao: TodoDao, private val userDao: UserDao, private val remoteApi: RemoteApi) {

    suspend fun fetchAndStoreUsers() : List<User> {
        val usersFromApi = remoteApi.getAllUsersFromApi()
        Log.d("UserNiCla", "fetchAndStoreUsers: ${usersFromApi}")
        val storeThereUsers = usersFromApi.users.map{ mapToUser(it)}
        userDao.insertAll(storeThereUsers)
        return usersFromApi.users
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


    suspend fun fetchandStoreTodos() : List<Todo>{
        val todosFromApi = remoteApi.getAllTodosFromApi()
        val storeTheTodos = todosFromApi.todos.map{ mapToTodo(it)}
        todoDao.insertAllTodos(storeTheTodos)
        return todosFromApi.todos
    }

    private fun mapToTodo(todo: Todo) : Todo {
        return Todo(
            id = todo.id,
            todo = todo.todo,
            completed = todo.completed,
            userId = todo.userId
        )
    }
}