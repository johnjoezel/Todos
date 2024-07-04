package com.example.todos.domain.repositories

import android.net.Uri
import androidx.lifecycle.LiveData
import com.example.todos.domain.remote.RemoteApi
import com.example.todos.domain.pojo.Todo
import com.example.todos.domain.pojo.User
import com.example.todos.domain.local.TodoDao
import com.example.todos.domain.local.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Repository @Inject constructor(private val todoDao: TodoDao, private val userDao: UserDao, private val remoteApi: RemoteApi) {

    private val users: LiveData<User> = userDao.getUsers()

    suspend fun fetchAndStoreUsers(){
        if (users.value == null) {
            withContext(Dispatchers.IO){
                val usersFromApi = remoteApi.getAllUsersFromApi()
                val storeTheUsers = usersFromApi.users.map { mapToUser(it) }
                userDao.insertAll(storeTheUsers)
                
            }

        }
    }

    suspend fun fetchUser(userId : Int) : User{
        return userDao.getUser(userId)
    }
    suspend fun updateUser(userId: Int, imageUri : Uri?){
        userDao.updateUser(userId, imageUri)
    }
    private fun mapToUser(user: User): User {
        return User(
            email = user.email,
            username = user.username,
            password = user.password
        )
    }



    // TODOS PART//
    suspend fun insertTodo(todo : Todo){
        withContext(Dispatchers.IO){
            todoDao.insertTodo(todo)

//            val response = remoteApi.insertTodo(todo)
        }


    }

    private fun mapToTodo(todo: Todo) : Todo {
        return Todo(
            id = todo.id,
            todo = todo.todo,
            completed = todo.completed,
            userId = todo.userId
        )
    }



//    suspend fun fetchTodos(userId: Int){
//        try{
//            val todos = todoDao.getUsersTodo(userId)
//            if(todos.value == null){
//                val todosFromApi = remoteApi.getAllTodosFromApi(userId)
//                val storeTheTodos = todosFromApi.todos.map{ mapToTodo(it)}
//                todoDao.insertAllTodos(storeTheTodos)
//            }
//        } catch (e: HttpException) {
//            MainApplication.showToastMessage(e.message.toString())
//        } catch (e: UnknownHostException) {
//            // Handle network unavailable
//            MainApplication.showToastMessage(e.message.toString())
//        } catch (e: Exception) {
//            // Handle other errors
//            MainApplication.showToastMessage(e.message.toString())
//        }
//    }

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