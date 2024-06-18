package com.example.todos.db

import androidx.lifecycle.LiveData
import com.example.todos.apis.RemoteApi
import com.example.todos.pojo.Todo
import com.example.todos.pojo.User

class Repository(private val todoDao: TodoDao, private val userDao: UserDao, private val remoteApi: RemoteApi) {

    val users: LiveData<User> = userDao.getUsers()
    val todos: LiveData<List<Todo>> = todoDao.getTodos()
    val availableTodos: LiveData<List<Todo>> = todoDao.getAvailableUserTodos()
    val completedTodos: LiveData<List<Todo>> = todoDao.getCompletedUserTodos()
    suspend fun fetchAndStoreUsers(){
        if (users.value == null) {
            val usersFromApi = remoteApi.getAllUsersFromApi()
            val storeThereUsers = usersFromApi.users.map { mapToUser(it) }
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
    suspend fun fetchTodos(userId: Int) {
        if(todos.value == null){
            val todosFromApi = remoteApi.getAllTodosFromApi(userId)
            val storeTheTodos = todosFromApi.todos.map{ mapToTodo(it)}
            todoDao.insertAllTodos(storeTheTodos)
        }
    }

    suspend fun deleteTodo(todo: Todo){
        todoDao.deleteTodo(todo)
    }

}